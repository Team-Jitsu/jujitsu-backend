package com.fightingkorea.platform.domain.order.entity;

import com.fightingkorea.platform.domain.user.entity.User;
import com.fightingkorea.platform.domain.video.entity.UserVideo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    @Comment("autoincrement")
    private Long orderId;

    @Column(name = "toss_order_id", nullable = false, length = 100)
    @Comment("UUID")
    private String tossOrderId;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "payment_key", length = 200)
    @Comment("결제 완료시 받는 key")
    private String paymentKey;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_videos_id")
    private UserVideo userVideo;

    public static Order createOrder(String tossOrderId,
                                    int amount,
                                    OrderStatus orderStatus,
                                    String paymentKey,
                                    User user,
                                    UserVideo userVideo) {
        return Order.builder()
                .tossOrderId(tossOrderId)
                .status(orderStatus)
                .amount(amount)
                .paymentKey(paymentKey)
                .user(user)
                .userVideo(userVideo)
                .build();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
