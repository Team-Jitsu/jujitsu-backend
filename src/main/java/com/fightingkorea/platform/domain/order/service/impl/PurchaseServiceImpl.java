package com.fightingkorea.platform.domain.order.service.impl;

import com.fightingkorea.platform.domain.earning.entity.EarningBuffer;
import com.fightingkorea.platform.domain.earning.repository.EarningBufferRepository;
import com.fightingkorea.platform.domain.order.dto.TossPaymentResponse;
import com.fightingkorea.platform.domain.order.dto.VideoPurchaseRequest;
import com.fightingkorea.platform.domain.order.entity.Order;
import com.fightingkorea.platform.domain.order.entity.OrderStatus;
import com.fightingkorea.platform.domain.order.repository.OrderRepository;
import com.fightingkorea.platform.domain.order.service.PurchaseService;
import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.user.exception.UserNotFoundException;
import com.fightingkorea.platform.domain.user.repository.UserRepository;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import com.fightingkorea.platform.domain.video.entity.Video;
import com.fightingkorea.platform.domain.video.exception.VideoNotExistsException;
import com.fightingkorea.platform.domain.video.repository.UserVideoRepository;
import com.fightingkorea.platform.domain.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Set;

/**
 * 결제 및 주문 관련 처리 서비스 구현 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private static final String TOSS_CONFIRM_URI = "confirm";
    // 결제 실패 처리가 허용된 주문 상태 목록
    private static final Set<OrderStatus> PAYMENT_FAIL_ALLOWED_STATUSES = Set.of(OrderStatus.READY, OrderStatus.IN_PROGRESS);

    private final OrderRepository orderRepository;
    private final EarningBufferRepository earningBufferRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final UserVideoRepository userVideoRepository;

    private final WebClient tossWebClient;

    /**
     * 영상 결제 처리 메서드
     */
    @Override
    public Order purchaseVideo(VideoPurchaseRequest request) {
        // 사용자 조회
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        // 영상 조회
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(VideoNotExistsException::new);

        // 결제 금액 검증
        if (!video.getPrice().equals(request.getAmount())) {
            throw new IllegalStateException("결제 금액이 실제 상품 금액과 일치하지 않음");
        }

        // Toss 결제 승인 요청
        TossPaymentResponse tossResponse = confirmPaymentFromToss(request);

        // 승인 결과 확인
        if (!"DONE".equals(tossResponse.getStatus())) {
            throw new RuntimeException("Toss 결제 승인 실패: " + tossResponse.getStatus());
        }


        // 주문, 구매, 수익 저장 처리
        return persistPurchase(user, video, request);
    }

    /**
     * 결제 실패 시 주문 상태를 ABORTED 로 변경
     */
    @Override
    public Order handlePaymentFailure(String tossOrderId, String errorMessage) {
        // 주문 조회
        Order order = orderRepository.findByTossOrderId(tossOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + tossOrderId));

        // 상태 검증
        if (!PAYMENT_FAIL_ALLOWED_STATUSES.contains(order.getStatus())) {
            throw new IllegalStateException("결제 실패 처리 불가 상태: " + order.getStatus());
        }

        // 상태 변경
        order.updateOrderStatus(OrderStatus.ABORTED);

        // 로그는 handler 에서 처리하므로 여기서 제거 가능

        return orderRepository.save(order);
    }

    /**
     * Toss 결제 승인 API 호출
     */
    private TossPaymentResponse confirmPaymentFromToss(VideoPurchaseRequest request) {
        try {
            return tossWebClient.post()
                    .uri(TOSS_CONFIRM_URI)
                    .bodyValue(Map.of(
                            "paymentKey", request.getPaymentKey(),
                            "orderId", request.getOrderId(),
                            "amount", String.valueOf(request.getAmount())
                    ))
                    .retrieve()
                    .bodyToMono(TossPaymentResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("[TOSS_API_ERROR] 요청 실패 - orderId={}, paymentKey={}, message={}",
                    request.getOrderId(), request.getPaymentKey(), e.getMessage(), e);
            throw new RuntimeException("Toss Payments API 호출 실패");
        }
    }

    /**
     * 결제 성공 시 주문, 유저영상, 정산 정보 저장 처리
     */
    private Order persistPurchase(User user, Video video, VideoPurchaseRequest request) {
        // 유저-영상 관계 생성 및 저장
        UserVideo userVideo = UserVideo.createUserVideo(user, video, video.getPrice());
        userVideoRepository.save(userVideo);

        // 주문 생성 및 저장
        Order order = Order.createOrder(
                request.getOrderId(),
                request.getAmount(),
                OrderStatus.DONE,
                request.getPaymentKey(),
                user,
                userVideo
        );
        orderRepository.save(order);

        // 수익 버퍼 생성 및 저장
        EarningBuffer earningBuffer = EarningBuffer.createEarningBuffer(
                video.getTrainer(),
                userVideo,
                video.getPrice()
        );
        earningBufferRepository.save(earningBuffer);

        // 결제 성공 로그
        log.info("[TOSS_PAYMENT_SUCCESS] 결제 완료 - orderId={}, userId={}", order.getOrderId(), user.getUserId());

        return order;
    }
}
