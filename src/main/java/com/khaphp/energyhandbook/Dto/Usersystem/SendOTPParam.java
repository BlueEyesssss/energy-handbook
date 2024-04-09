package com.khaphp.energyhandbook.Dto.Usersystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendOTPParam {
    private String gmail;
    private int otp;
}
