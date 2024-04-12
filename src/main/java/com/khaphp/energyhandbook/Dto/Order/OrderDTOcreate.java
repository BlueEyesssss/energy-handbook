package com.khaphp.energyhandbook.Dto.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.OrderDetail;
import com.khaphp.energyhandbook.Entity.PaymentOrder;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.OrderDetailDTOcreate;
import com.khaphp.energyhandbook.Util.ValidData.MethodOrder.ValidMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
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
//    private String phoneGuest;
//    private String nameGuest;
//    private String address;
}
