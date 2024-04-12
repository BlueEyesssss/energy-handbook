package com.khaphp.energyhandbook.Dto.Order;

import com.khaphp.energyhandbook.OrderDetailDTOcreate;
import com.khaphp.energyhandbook.Util.ValidData.MethodOrder.ValidMethod;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ParamOrderShipperId {
    private String orderId;
    private String shipperId;
}
