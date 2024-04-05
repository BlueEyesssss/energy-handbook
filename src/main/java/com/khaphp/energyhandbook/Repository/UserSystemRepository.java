package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, String> {
    UserSystem findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


}
