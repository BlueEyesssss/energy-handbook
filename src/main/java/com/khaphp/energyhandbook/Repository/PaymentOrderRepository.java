package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Order;
import com.khaphp.energyhandbook.Entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, String> {
}
