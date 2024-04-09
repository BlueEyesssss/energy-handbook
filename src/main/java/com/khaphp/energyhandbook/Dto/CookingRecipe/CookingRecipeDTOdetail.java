package com.khaphp.energyhandbook.Dto.CookingRecipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
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
public class CookingRecipeDTOdetail {
    private String id;
    private String name;
    private String productImg;
    private String level;
    private float timeCook;
    private Date updateDate;
    private short mealServing;
    private String description;
    private String status;

    private UserSystemDTOviewInOrtherEntity employeeV;

    private UserSystemDTOviewInOrtherEntity customerV;

    private List<RecipeIngredients> recipeIngredients;

    private List<FoodTutorial> foodTutorials;

//    @OneToMany(mappedBy = "cookingRecipe")
//    @JsonIgnore
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "cookingRecipe")
//    @JsonIgnore
//    private List<Interact> interacts;
}
