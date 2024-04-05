package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.keys.InteractKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Interact {
    @EmbeddedId
    private InteractKey id;
    private String type;
    private short star;

    @ManyToOne
    @MapsId("customerId")
    @JsonIgnore
    private UserSystem customer;

    @ManyToOne
    @MapsId("cookingRecipeId")
    @JsonIgnore
    private CookingRecipe cookingRecipe;
}
