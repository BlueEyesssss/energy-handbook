package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.TypeInteract;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOcreate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.CookingRecipeRepository;
import com.khaphp.energyhandbook.Service.CookingRecipeService;
import com.khaphp.energyhandbook.Service.InteractService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InteractServiceImpl implements InteractService {
    @Autowired
    private CookingRecipeService cookingRecipeService;

    @Autowired
    private CookingRecipeRepository cookingRecipeRepository;

    @Autowired
    private UserSystemService userSystemService;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;
    @Override
    public ResponseObject<Object> create(InteractDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            userSystem.setImgUrl(userSystem.getImgUrl().substring(linkBucket.length()));

            CookingRecipe cookingRecipe = cookingRecipeRepository.findById(object.getCookingRecipeId()).orElse(null);
            if(cookingRecipe == null){
                throw new Exception("cookingRecipe not found");
            }

            if(object.getTypeInteract().equals(TypeInteract.LIKE.toString())){
                cookingRecipe.getUserLikes().add(userSystem);
            }else if(object.getTypeInteract().equals(TypeInteract.SHARE.toString())){
                cookingRecipe.getUserShares().add(userSystem);
            }else if(object.getTypeInteract().equals(TypeInteract.VOTE.toString())){
                //...
            }else if(object.getTypeInteract().equals(TypeInteract.REPORT.toString())){
                cookingRecipe.getUserReports().add(userSystem);
            }
            cookingRecipeRepository.save(cookingRecipe);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success " + object.getTypeInteract())
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> delete(InteractDTOcreate object) {
        try{
            UserSystem userSystem = (UserSystem) userSystemService.getDetail(object.getCustomerId()).getData();
            if(userSystem == null){
                throw new Exception("user not found");
            }
            userSystem.setImgUrl(userSystem.getImgUrl().substring(linkBucket.length()));

            CookingRecipe cookingRecipe = cookingRecipeRepository.findById(object.getCookingRecipeId()).orElse(null);
            if(cookingRecipe == null){
                throw new Exception("cookingRecipe not found");
            }

            if(object.getTypeInteract().equals(TypeInteract.LIKE.toString())){
                cookingRecipe.getUserLikes().remove(userSystem);
            }else if(object.getTypeInteract().equals(TypeInteract.SHARE.toString())){
                cookingRecipe.getUserShares().remove(userSystem);
            }else if(object.getTypeInteract().equals(TypeInteract.VOTE.toString())){
                //...
            }else if(object.getTypeInteract().equals(TypeInteract.REPORT.toString())){
                cookingRecipe.getUserReports().remove(userSystem);
            }
            cookingRecipeRepository.save(cookingRecipe);
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }
}
