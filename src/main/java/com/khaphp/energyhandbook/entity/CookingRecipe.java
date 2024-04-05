package com.khaphp.energyhandbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CookingRecipe {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private String name;
    private String productImg;
    private String level;
    private Time timeCook;
    private Date updateDate;
    private short mealServing;
    private String description;
    private String status;

    @ManyToOne
    private UserSystem employee;

    @ManyToOne
    private UserSystem customer;

    @OneToMany(mappedBy = "cookingRecipe")
    private List<RecipeIngredients> recipeIngredients;

    @OneToMany(mappedBy = "cookingRecipe")
    private List<FoodTutorial> foodTutorials;

    @OneToMany(mappedBy = "cookingRecipe")
    private List<Comment> comments;

    @OneToMany(mappedBy = "cookingRecipe")
    private List<Interact> interacts;
}
