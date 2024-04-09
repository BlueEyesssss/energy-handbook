package com.khaphp.energyhandbook.Dto.Usersystem;

import com.khaphp.energyhandbook.Util.ValidData.Birthday.ValidBirthday;
import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGender;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSystemDTOUpdate {
    private String id;
    @Size(min = 5, message = "name length must be at least 5 characters")
    @Size(max = 40, message = "name length must be at most 40 characters")
    private String name;
    @ValidBirthday
    private long birthdayL;
    @ValidGender
    private String gender;
}
