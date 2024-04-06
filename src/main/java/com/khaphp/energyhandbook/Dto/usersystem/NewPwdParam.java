package com.khaphp.energyhandbook.Dto.usersystem;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewPwdParam {
    private String id;
    @Size(min = 5, message = "password length must be at least 5 characters")
    @Size(max = 20, message = "password length must be at most 20 characters")
    private String newPassword;
}
