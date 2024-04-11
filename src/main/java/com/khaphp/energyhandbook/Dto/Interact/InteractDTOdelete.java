package com.khaphp.energyhandbook.Dto.Interact;

import com.khaphp.energyhandbook.Util.ValidData.TypeInteract.ValidTypeInteract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InteractDTOdelete {
    private String customerId;
    private String ownerId;
    private String cookingRecipeId;
    @ValidTypeInteract
    private String typeInteract;
}
