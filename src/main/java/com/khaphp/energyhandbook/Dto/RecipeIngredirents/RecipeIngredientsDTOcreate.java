package com.khaphp.energyhandbook.Dto.RecipeIngredirents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeIngredientsDTOcreate {
    private String cookingRecipeId;
    private List<RecipeIngredientsDTOcreateItem> items;
}
