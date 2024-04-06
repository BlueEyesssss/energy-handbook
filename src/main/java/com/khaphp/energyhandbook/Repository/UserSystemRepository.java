package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, String> {
    UserSystem findByEmail(String email);
    Optional<UserSystem> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
