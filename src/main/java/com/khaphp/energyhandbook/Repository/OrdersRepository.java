package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, String> {
}
