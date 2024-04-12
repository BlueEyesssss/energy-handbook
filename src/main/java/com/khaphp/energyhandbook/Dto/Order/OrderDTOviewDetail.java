package com.khaphp.energyhandbook.Dto.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Dto.OrderDetail.OrderDetailDToviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Entity.OrderDetail;
import com.khaphp.energyhandbook.Entity.PaymentOrder;
import com.khaphp.energyhandbook.Entity.UserSystem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTOviewDetail {
    private String id;
    private Date createDate;
    private Date updateDate;
    private Date deliveryTime;
    private String status;
    private float totalPrice;
    private UserSystemDTOviewInOrtherEntity employeeV;
    private UserSystemDTOviewInOrtherEntity shipperV;
    private UserSystemDTOviewInOrtherEntity customerV;
    private List<OrderDetailDToviewInOrtherEntity> orderDetailsV;
    private PaymentOrder paymentOrder;
}
