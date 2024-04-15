package com.khaphp.energyhandbook.Dto.Order;

import com.khaphp.energyhandbook.Dto.OrderDetail.OrderDetailDTOcreate;
import com.khaphp.energyhandbook.Util.ValidData.MethodOrder.ValidMethod;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTOcreate {
    private String customerId;
    private List<OrderDetailDTOcreate> orderDetails;
    @ValidMethod
    private String method;
    //--guest payment
    private String phoneGuest;
    private String nameGuest;
    private String address;
}
