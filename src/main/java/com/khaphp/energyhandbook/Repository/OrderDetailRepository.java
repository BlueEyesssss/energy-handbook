package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Order;
import com.khaphp.energyhandbook.Entity.OrderDetail;
import com.khaphp.energyhandbook.Entity.keys.OrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {
}
