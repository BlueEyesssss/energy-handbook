package com.khaphp.energyhandbook.Dto.OrderDetail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Dto.Food.FoodDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Entity.Food;
import com.khaphp.energyhandbook.Entity.Order;
import com.khaphp.energyhandbook.Entity.keys.OrderDetailKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDToviewInOrtherEntity {
    private FoodDTOviewInOrtherEntity foodV;
    private float amount;
    private float price;
}
