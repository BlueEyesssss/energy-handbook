package com.khaphp.energyhandbook.Dto.Order;

import com.khaphp.energyhandbook.Util.ValidData.StatusOrder.ValidStatusOrder;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTOupdate {
    private String orderId;
    private String employeeId;
    @ValidStatusOrder
    private String status;
}
