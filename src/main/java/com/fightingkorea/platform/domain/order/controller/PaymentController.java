package com.fightingkorea.platform.domain.order.controller;

import com.fightingkorea.platform.domain.order.dto.PaymentFailRequest;
import com.fightingkorea.platform.domain.order.dto.VideoPurchaseRequest;
import com.fightingkorea.platform.domain.order.entity.Order;
import com.fightingkorea.platform.domain.order.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PurchaseService purchaseService;

    // 결제 완료 처리 엔드포인트
    @PostMapping("/complete")
    public Order completePayment(@RequestBody VideoPurchaseRequest request) {
        return purchaseService.purchaseVideo(request);
    }

    // 결제 실패 처리 엔드포인트
    @PostMapping("/fail")
    public Order failPayment(@RequestBody PaymentFailRequest request) {
        return purchaseService.handlePaymentFailure(request.getTossOrderId(), request.getErrorMessage());
    }
}
