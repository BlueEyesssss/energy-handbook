package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.keys.VotesKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Votes {
    @EmbeddedId
    private VotesKey id;
    private int star;

    @ManyToOne
    @MapsId("cookingRecipeId")
    @JsonIgnore
    private CookingRecipe cookingRecipe;

    @ManyToOne
    @MapsId("customerId")
    @JsonIgnore
    private UserSystem customer;
}
