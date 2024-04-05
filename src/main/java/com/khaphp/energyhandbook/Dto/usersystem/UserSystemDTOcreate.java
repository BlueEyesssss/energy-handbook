package com.khaphp.energyhandbook.Dto.usersystem;

import com.khaphp.energyhandbook.Util.ValidData.Birthday.ValidBirthday;
import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSystemDTOcreate {
    @Size(min = 5, message = "name length must be at least 5 characters")
    @Size(max = 40, message = "name length must be at most 40 characters")
    private String name;
    @Size(min = 5, message = "Username length must be at least 5 characters")
    @Size(max = 40, message = "Username length must be at most 40 characters")
    private String username;
    @Size(min = 5, message = "password length must be at least 5 characters")
    @Size(max = 20, message = "password length must be at most 20 characters")
    private String password;
    @Email
    private String email;
    @ValidBirthday
    private long birthdayL;
    @ValidGender
    private String gender;
//    private String status;
//    private String role;
//    private boolean premium;
}
