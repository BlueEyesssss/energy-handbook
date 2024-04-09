package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOcreate;
import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOcreateItem;
import com.khaphp.energyhandbook.Dto.FoodTutorial.FoodTutorialDTOupdate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOcreate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOupdateItem;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface FoodTutorialService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex, String cookingRecipeId);
    ResponseObject<Object> getDetail(String id);
    ResponseObject<Object> create(FoodTutorialDTOcreate object);
    ResponseObject<Object> update(FoodTutorialDTOupdate object);
    ResponseObject<Object> updateImage(String id, MultipartFile file);
    ResponseObject<Object> delete(String id);
}
