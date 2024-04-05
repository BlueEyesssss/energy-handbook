package com.khaphp.energyhandbook.entity;

import com.khaphp.energyhandbook.entity.keys.InteractKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

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
    private UserSystem customer;

    @ManyToOne
    @MapsId("cookingRecipeId")
    private CookingRecipe cookingRecipe;
}
