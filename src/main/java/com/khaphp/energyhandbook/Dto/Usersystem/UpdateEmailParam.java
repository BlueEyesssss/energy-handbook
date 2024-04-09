package com.khaphp.energyhandbook.Dto.Usersystem;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEmailParam {
    private String id;
    @Email
    private String email;
}
