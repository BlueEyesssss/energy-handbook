package com.khaphp.energyhandbook.Dto.CookingRecipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.*;
import com.khaphp.energyhandbook.Util.ValidData.Level.ValidLevel;
import com.khaphp.energyhandbook.Util.ValidData.StatusRecipe.ValidStatusReceipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CookingRecipeDTOcreate {
    private String customerId;
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;
    @ValidLevel
    private String level;
    @Min(value = 0, message = "Time must be greater than 0")
    @Max(value = 48, message = "Time must be less than 48 hours")
    private float timeCook;
    @Min(value = 0, message = "mealServing must be greater than 0")
    private short mealServing;
    private String description;
    @ValidStatusReceipe
    private String status;
}
