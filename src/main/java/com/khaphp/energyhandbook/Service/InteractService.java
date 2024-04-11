package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOcreate;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOupdate;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOcreate;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOdelete;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface InteractService {
    ResponseObject<Object> create(InteractDTOcreate object);
    ResponseObject<Object> delete(InteractDTOdelete id);
}
