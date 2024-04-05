package com.khaphp.energyhandbook.Entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderDetailKey implements Serializable {
    @Column(name = "food_id", columnDefinition = "VARCHAR(36)")
    private String foodId;
    @Column(name = "order_id", columnDefinition = "VARCHAR(36)")
    private String orderId;
}
