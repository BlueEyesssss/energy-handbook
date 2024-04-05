package com.khaphp.energyhandbook.entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class InteractKey implements Serializable {
    @Column(name = "customer_id", columnDefinition = "VARCHAR(36)")
    private String customerId;
    @Column(name = "cooking_recipe_id", columnDefinition = "VARCHAR(36)")
    private String cookingRecipeId;
}
