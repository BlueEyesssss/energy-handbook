package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookingRecipeRepository extends JpaRepository<CookingRecipe, String> {
    Page<CookingRecipe> findByCustomerIdAndStatus(String customerId, String status, PageRequest pageRequest);

    Page<CookingRecipe> findByStatus(String status, PageRequest pageRequest);
}
