package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private float timeCook;
    private Date updateDate;
    private short mealServing;
    private String description;
    private String status;

    @ManyToOne
    @JsonIgnore
    private UserSystem employee;

    @ManyToOne
    @JsonIgnore
    private UserSystem customer;

    @OneToMany(mappedBy = "cookingRecipe")
    @JsonIgnore
    private List<RecipeIngredients> recipeIngredients;

    @OneToMany(mappedBy = "cookingRecipe")
    @JsonIgnore
    private List<FoodTutorial> foodTutorials;

    @OneToMany(mappedBy = "cookingRecipe")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "cookingRecipe")
    @JsonIgnore
    private List<Votes> votes;

    @ManyToMany
    @JoinTable(
        name = "likes",
        joinColumns = @JoinColumn(name = "cooking_recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @JsonIgnore
    private List<UserSystem> userLikes;

//    @ManyToMany
//    @JoinTable(
//            name = "votes",
//            joinColumns = @JoinColumn(name = "cooking_recipe_id"),
//            inverseJoinColumns = @JoinColumn(name = "customer_id")
//    )
//    @JsonIgnore
//    private List<UserSystem> userVotes;

    @ManyToMany
    @JoinTable(
        name = "reports",
        joinColumns = @JoinColumn(name = "cooking_recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @JsonIgnore
    private List<UserSystem> userReports;

    @ManyToMany
    @JoinTable(
            name = "shares",
            joinColumns = @JoinColumn(name = "cooking_recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    @JsonIgnore
    private List<UserSystem> userShares;
}
