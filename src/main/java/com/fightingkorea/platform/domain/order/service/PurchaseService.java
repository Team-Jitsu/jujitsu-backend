package com.fightingkorea.platform.domain.order.service;

import com.fightingkorea.platform.domain.order.dto.PaymentRequestDto;
import com.fightingkorea.platform.domain.order.dto.PaymentRequestRequest;
import com.fightingkorea.platform.domain.order.dto.PaymentCompleteDto;
import com.fightingkorea.platform.domain.order.dto.TossPaymentWebhookRequest;
import com.fightingkorea.platform.domain.order.dto.PaymentStatusDto;
import com.fightingkorea.platform.domain.order.dto.VideoPurchaseRequest;
import com.fightingkorea.platform.domain.order.entity.Order;

public interface PurchaseService {
    Order purchaseVideo(VideoPurchaseRequest request);

    Order handlePaymentFailure(String tossOrderId, String errorMessage);

    PaymentStatusDto getPaymentStatus(String paymentKey);

    PaymentRequestDto requestPayment(PaymentRequestRequest request);

    PaymentCompleteDto completePayment(TossPaymentWebhookRequest request);
}