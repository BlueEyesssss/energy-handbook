package com.khaphp.energyhandbook.entity;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSystem {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Date birthday;
    private String imgUrl;
    private String gender;
    private String status;
    private String role;
    private boolean premium;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "employee")
    private List<News> news;

    @OneToOne(mappedBy = "customer")
    private Wallet wallet;

    @OneToMany(mappedBy = "employee")
    private List<Food> foods;

    @OneToMany(mappedBy = "employee")
    private List<Order> employeeOrders;

    @OneToMany(mappedBy = "shipper")
    private List<Order> shipperOrders;

    @OneToMany(mappedBy = "customer")
    private List<Order> customerOrders;

    @OneToMany(mappedBy = "employee")
    private List<CookingRecipe> employeeCookingRecipes;

    @OneToMany(mappedBy = "customer")
    private List<CookingRecipe> customerCookingRecipes;

    @OneToMany(mappedBy = "customer")
    private List<Comment> comments;

    @OneToMany(mappedBy = "customer")
    private List<Interact> interacts;
}
