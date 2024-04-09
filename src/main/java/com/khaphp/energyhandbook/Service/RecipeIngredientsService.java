package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOcreate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOupdateItem;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeIngredientsService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex, String cookingRecipeId);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(RecipeIngredientsDTOcreate object);
    ResponseObject<Object> update(RecipeIngredientsDTOupdateItem object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
}
