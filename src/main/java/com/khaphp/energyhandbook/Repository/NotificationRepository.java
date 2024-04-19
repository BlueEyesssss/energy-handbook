package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Food;
import com.khaphp.energyhandbook.Entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    Page<Notification> findByUserId(String userId, PageRequest pageRequest);
}
