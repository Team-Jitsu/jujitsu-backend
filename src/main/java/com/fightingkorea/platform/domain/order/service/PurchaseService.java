package com.fightingkorea.platform.domain.order.service;

import com.fightingkorea.platform.domain.order.dto.PaymentStatusDto;
import com.fightingkorea.platform.domain.order.dto.VideoPurchaseRequest;
import com.fightingkorea.platform.domain.order.entity.Order;

public interface PurchaseService {
    Order purchaseVideo(VideoPurchaseRequest request);

    Order handlePaymentFailure(String tossOrderId, String errorMessage);

    PaymentStatusDto getPaymentStatus(String paymentKey);
}