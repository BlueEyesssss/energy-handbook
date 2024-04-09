package com.khaphp.energyhandbook.Dto.FoodTutorial;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodTutorialDTOcreateItem {
    @Size(min = 0, message = "numberOrder must bigger than 0")
    private short numberOrder;
    private String description;
}
