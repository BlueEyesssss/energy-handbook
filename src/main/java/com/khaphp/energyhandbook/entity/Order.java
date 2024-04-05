package com.khaphp.energyhandbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private Date createDate;
    private Date updateDate;
    private Date deliveryTime;
    private String status;
    private float totalPrice;

    @ManyToOne
    private UserSystem employee;

    @ManyToOne
    private UserSystem shipper;

    @ManyToOne
    private UserSystem customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    private List<PaymentOrder> paymentOrders;
}
