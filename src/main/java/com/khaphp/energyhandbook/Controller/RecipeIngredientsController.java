package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.News.NewsDTOcreate;
import com.khaphp.energyhandbook.Dto.News.NewsDTOupdate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOcreate;
import com.khaphp.energyhandbook.Dto.RecipeIngredirents.RecipeIngredientsDTOupdateItem;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.NewsService;
import com.khaphp.energyhandbook.Service.RecipeIngredientsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/recipe-ingredients")
//@SecurityRequirement(name = "EnergyHandbook")
public class RecipeIngredientsController {
    @Autowired
    private RecipeIngredientsService recipeIngredientsService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(defaultValue = "1") int pageIndex,
                                    @RequestParam(defaultValue = "") String cookingRecipeId){
        ResponseObject responseObject = recipeIngredientsService.getAll(pageSize, pageIndex, cookingRecipeId);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
    @GetMapping("/detail")
    public ResponseEntity<?> getObject(String id){
        ResponseObject responseObject = recipeIngredientsService.getDetail(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PostMapping
    public ResponseEntity<?> createObject(@RequestBody @Valid RecipeIngredientsDTOcreate object){
        ResponseObject responseObject = recipeIngredientsService.create(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping(
            path = "/img",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateImg(@RequestParam("id") String id,
                                       @RequestParam("file") MultipartFile file){
        ResponseObject responseObject = recipeIngredientsService.updateImage(id, file);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<?> updateObject(@RequestBody @Valid RecipeIngredientsDTOupdateItem object){
        ResponseObject responseObject = recipeIngredientsService.update(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteObject(String id){
        ResponseObject responseObject = recipeIngredientsService.delete(id);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
