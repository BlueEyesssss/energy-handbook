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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Food {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private String name;
    private float calo;
    private String unit;
    private float stock;
    private float price;
    private short sale; //from 1 to 100
    private String img;
    private String location;
    private Date updateDate;
    private String status;

    @ManyToOne
    private UserSystem employee;

    @OneToMany(mappedBy = "food")
    private List<OrderDetail> orderDetails;
}
