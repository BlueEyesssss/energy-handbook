package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.FoodEncylopedia;
import com.khaphp.energyhandbook.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodEncylopediaRepository extends JpaRepository<FoodEncylopedia, String> {
    FoodEncylopedia findByName(String name);
}
