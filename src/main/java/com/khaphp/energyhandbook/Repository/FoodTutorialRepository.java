package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.FoodTutorial;
import com.khaphp.energyhandbook.Entity.RecipeIngredients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTutorialRepository extends JpaRepository<FoodTutorial, String> {
    Page<FoodTutorial> findAllByCookingRecipeId(String id, PageRequest of);
}
