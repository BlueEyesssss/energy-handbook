package com.khaphp.energyhandbook.Dto.Wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Entity.WalletTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletDTOupdate {
    private String customerId;
    private int balance;
}
