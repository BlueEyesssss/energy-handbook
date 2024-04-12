package com.khaphp.energyhandbook.Dto.Food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.FoodEncylopedia;
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
public class FoodDTOviewInOrtherEntity {
    private String id;
    private String name;
    private String img;
}
