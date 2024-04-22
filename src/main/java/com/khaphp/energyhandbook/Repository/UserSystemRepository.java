package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, String> {
    UserSystem findByEmail(String email);
    Optional<UserSystem> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);


    @Query(value = "SELECT * FROM energy_handbook.user_system where birthday like ?1 and role = ?2", nativeQuery = true)
    List<UserSystem> findByBirthdayByRole(String dateNoew, String role);
}
