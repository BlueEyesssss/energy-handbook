package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.RecipeIngredients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, String> {
    Page<RecipeIngredients> findAllByCookingRecipeId(String id, PageRequest of);
}
