package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Wallet;
import com.khaphp.energyhandbook.Entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
}
