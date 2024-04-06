package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSystem implements UserDetails {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private Date birthday;
    private String imgUrl;
    private String gender;
    private String status;
    private String role;
    private boolean premium;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<News> news;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private Wallet wallet;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Food> foods;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Order> employeeOrders;

    @OneToMany(mappedBy = "shipper")
    @JsonIgnore
    private List<Order> shipperOrders;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Order> customerOrders;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<CookingRecipe> employeeCookingRecipes;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<CookingRecipe> customerCookingRecipes;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Interact> interacts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
