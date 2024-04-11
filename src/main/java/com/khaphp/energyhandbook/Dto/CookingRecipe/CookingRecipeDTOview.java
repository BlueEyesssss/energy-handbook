package com.khaphp.energyhandbook.Dto.CookingRecipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.*;
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
public class CookingRecipeDTOview {
    private String id;
    private String name;
    private String productImg;
    private String level;
    private float timeCook;
    private Date updateDate;
    private short mealServing;
    private String description;
    private String status;
    private int cmtSize;
    private int like;
    private float star;
    private int vote;
}
