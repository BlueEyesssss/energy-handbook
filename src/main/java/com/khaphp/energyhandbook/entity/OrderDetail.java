package com.khaphp.energyhandbook.entity;

import com.khaphp.energyhandbook.entity.keys.OrderDetailKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

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
    private Order order;

    @ManyToOne
    @MapsId("foodId")
    private Food food;
}
