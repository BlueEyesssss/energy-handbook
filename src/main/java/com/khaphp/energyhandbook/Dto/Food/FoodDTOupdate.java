package com.khaphp.energyhandbook.Dto.Food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.FoodEncylopedia;
import com.khaphp.energyhandbook.Entity.OrderDetail;
import com.khaphp.energyhandbook.Entity.UserSystem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class FoodDTOupdate {
    private String id;
    @Size(min=1, max=255, message = "name length from 1 to 255")
    private String name;
    @Size(min=1, max=50, message = "unit length from 1 to 50")
    private String unit;
    @Min(value = 1, message = "stock must be greater than 0")
    private float stock;
    @Min(value = 1, message = "price must be greater than 0")
    private float price;
    @Min(value = 0, message = "sale from 0 to 100")
    private short sale; //from 1 to 100
    @Size(min=1, max=255, message = "location length from 1 to 255")
    private String location;
}
