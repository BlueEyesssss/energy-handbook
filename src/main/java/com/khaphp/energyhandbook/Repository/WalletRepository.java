package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
}
