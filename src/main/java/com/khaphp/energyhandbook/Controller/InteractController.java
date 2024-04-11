package com.khaphp.energyhandbook.Controller;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOcreate;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOupdate;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOcreate;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOdelete;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Service.CookingRecipeService;
import com.khaphp.energyhandbook.Service.InteractService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/interact")
//@SecurityRequirement(name = "EnergyHandbook")
public class InteractController {
    @Autowired
    private InteractService interactService;

    @PostMapping
    @Operation(description = "LIKE, VOTE, REPORT -> customerID là thàng cus đang làm vc dó             \n" +
            "SHARE -> customerId là thằng Owner của cái cooking recipe đó, gmails là nơi chứa các email để cho thằng owner share           \n" +
            "VOTE -> cần the star để hiê số sao             \n" +
            "LIKE, REPORT (customerId, cookingRecipeId, typeInteract)           \n" +
            "VOTE (customerId, cookingRecipeId, typeInteract, star)           \n" +
            "SHARE (customerId, cookingRecipeId, typeInteract, gmails)")
    public ResponseEntity<?> createObject(@RequestBody @Valid InteractDTOcreate object){
        ResponseObject responseObject = interactService.create(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }

    @DeleteMapping
    @Operation(description = "LIKE, REPORT, VOTE (customerId, cookingRecipeId, typeInteract)              \n" +
            "SHARE (customerId, cookingRecipeId, typeInteract, ownerId) -> owner là thằng chủ, cusId là thằng customer cần xóa share")
    public ResponseEntity<?> deleteObject(@RequestBody @Valid InteractDTOdelete object){
        ResponseObject responseObject = interactService.delete(object);
        if(responseObject.getCode() == 200){
            return ResponseEntity.ok(responseObject);
        }
        return ResponseEntity.badRequest().body(responseObject);
    }
}
