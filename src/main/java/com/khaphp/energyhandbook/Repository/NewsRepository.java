package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
}
