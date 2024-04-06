package com.khaphp.energyhandbook.Dto.usersystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.*;
import com.khaphp.energyhandbook.Util.ValidData.Birthday.ValidBirthday;
import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
