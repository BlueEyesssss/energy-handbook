package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private UserSystem employee;

    @ManyToOne
    @JsonIgnore
    private UserSystem shipper;

    @ManyToOne
    @JsonIgnore
    private UserSystem customer;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<PaymentOrder> paymentOrders;
}
