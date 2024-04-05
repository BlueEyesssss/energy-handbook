package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.keys.OrderDetailKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {
    @EmbeddedId
    private OrderDetailKey id;
    private float amount;
    private float price;

    @ManyToOne
    @MapsId("orderId")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @MapsId("foodId")
    @JsonIgnore
    private Food food;
}
