package com.khaphp.energyhandbook.Dto.OrderDetail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailDTOcreate {
    private String foodId;
    private float amount;
    private float price;
}
