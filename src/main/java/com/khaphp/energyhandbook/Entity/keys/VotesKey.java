package com.khaphp.energyhandbook.Entity.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VotesKey implements Serializable {
    @Column(name = "customer_id", columnDefinition = "VARCHAR(36)")
    private String customerId;
    @Column(name = "cooking_recipe_id", columnDefinition = "VARCHAR(36)")
    private String cookingRecipeId;
}
