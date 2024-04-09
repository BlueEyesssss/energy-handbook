package com.khaphp.energyhandbook.Dto.RecipeIngredirents;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeIngredientsDTOcreateItem {
    @Size(min = 1, message = "Name must not be empty")
    private String name;
    @Size(min = 1, message = "amount must not be empty")
    private String amount;
    private String note;
}
