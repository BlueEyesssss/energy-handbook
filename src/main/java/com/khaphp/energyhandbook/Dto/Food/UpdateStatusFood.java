package com.khaphp.energyhandbook.Dto.Food;

import com.khaphp.energyhandbook.Util.ValidData.Status.ValidStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusFood {
    private String id;
    @ValidStatus
    private String status;
}
