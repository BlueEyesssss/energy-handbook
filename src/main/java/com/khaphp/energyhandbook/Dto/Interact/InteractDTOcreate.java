package com.khaphp.energyhandbook.Dto.Interact;

import com.khaphp.energyhandbook.Util.ValidData.TypeInteract.ValidTypeInteract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InteractDTOcreate {
    private String customerId;
    private String cookingRecipeId;
    @ValidTypeInteract
    private String typeInteract;
    private int star;
}
