package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOcreate;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOupdate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface CookingRecipeService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex, String customerId);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(CookingRecipeDTOcreate object);
    ResponseObject<Object> update(CookingRecipeDTOupdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
}
