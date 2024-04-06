package com.khaphp.energyhandbook.Dto.usersystem;

import com.khaphp.energyhandbook.Util.ValidData.Status.ValidStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusParam {
    private String id;
    @ValidStatus
    private String status;
}
