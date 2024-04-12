package com.khaphp.energyhandbook.Entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class OrderDetailKey implements Serializable {
    @Column(name = "food_id", columnDefinition = "VARCHAR(36)")
    private String foodId;
    @Column(name = "order_id", columnDefinition = "VARCHAR(36)")
    private String orderId;
}
