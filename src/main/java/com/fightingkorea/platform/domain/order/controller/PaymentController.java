package com.fightingkorea.platform.domain.order.controller;

import com.fightingkorea.platform.domain.order.dto.PaymentCompleteDto;
import com.fightingkorea.platform.domain.order.dto.PaymentFailRequest;
import com.fightingkorea.platform.domain.order.dto.PaymentRequestDto;
import com.fightingkorea.platform.domain.order.dto.PaymentRequestRequest;
import com.fightingkorea.platform.domain.order.dto.PaymentStatusDto;
import com.fightingkorea.platform.domain.order.dto.TossPaymentWebhookRequest;
import com.fightingkorea.platform.domain.order.entity.Order;
import com.fightingkorea.platform.domain.order.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PurchaseService purchaseService;

    // 결제 요청 생성 엔드포인트
    @PostMapping("/request")
    public PaymentRequestDto requestPayment(@RequestBody PaymentRequestRequest request) {
        return purchaseService.requestPayment(request);
    }

    // 결제 완료 처리 엔드포인트
    @PostMapping("/complete")
    public PaymentCompleteDto completePayment(@RequestBody TossPaymentWebhookRequest request) {
        return purchaseService.completePayment(request);
    }

    // 결제 실패 처리 엔드포인트
    @PostMapping("/fail")
    public Order failPayment(@RequestBody PaymentFailRequest request) {
        return purchaseService.handlePaymentFailure(request.getTossOrderId(), request.getErrorMessage());
    }

    // 결제 상태 조회 엔드포인트
    @GetMapping("/{paymentKey}/status")
    public PaymentStatusDto getPaymentStatus(@PathVariable String paymentKey) {
        return purchaseService.getPaymentStatus(paymentKey);
    }
}
