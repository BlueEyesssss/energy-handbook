package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Food;
import com.khaphp.energyhandbook.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {
}
