package com.khaphp.energyhandbook.repository;

import com.khaphp.energyhandbook.entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<UserSystem, String> {
}
