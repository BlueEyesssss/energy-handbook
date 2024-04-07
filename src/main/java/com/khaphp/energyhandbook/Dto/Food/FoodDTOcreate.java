package com.khaphp.energyhandbook.Dto.Food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.OrderDetail;
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
public class FoodDTOcreate {
    private String name;
    private float calo;
    private String unit;
//    private float stock;
//    private float price;
//    private short sale; //from 1 to 100
//    private String img;
//    private String location;
//    private Date updateDate;
//    private String status;
}
