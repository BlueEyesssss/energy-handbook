package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.TypeInteract;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOcreate;
import com.khaphp.energyhandbook.Dto.Interact.InteractDTOdelete;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.News;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Entity.Votes;
import com.khaphp.energyhandbook.Entity.keys.VotesKey;
import com.khaphp.energyhandbook.Repository.CookingRecipeRepository;
import com.khaphp.energyhandbook.Repository.UserSystemRepository;
import com.khaphp.energyhandbook.Repository.VotesRepository;
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
    private UserSystemRepository userSystemRepository;

    @Autowired
    private VotesRepository votesRepository;

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
                //kiểm tra chỉnh chủ mới đc quyền share
                if(!userSystem.getUsername().equals(cookingRecipe.getCustomer().getUsername())){
                    throw new Exception("you can't share this recipe because you don't own it");
                }
                if(object.getGmails().size() > 0){
                    for(String gmail : object.getGmails()){
                        UserSystem user = userSystemRepository.findByEmail(gmail);
                        if(user == null){
                            throw new Exception("user with gmail "+gmail+" not found");
                        }
                        user.setImgUrl(user.getImgUrl().substring(linkBucket.length()));
                        cookingRecipe.getUserShares().add(user);
                    }
                }
            }else if(object.getTypeInteract().equals(TypeInteract.VOTE.toString())){
                Votes votes = new Votes();
                votes.setId(VotesKey.builder()
                        .cookingRecipeId(object.getCookingRecipeId())
                        .customerId(object.getCustomerId()).build());
                votes.setCookingRecipe(cookingRecipe);
                votes.setCustomer(userSystem);
                votes.setStar(object.getStar());
                votesRepository.save(votes);
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
    public ResponseObject<Object> delete(InteractDTOdelete object) {
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
                //kiểm tra chỉnh chủ mới đc quyền
                UserSystem owner = userSystemRepository.findById(object.getOwnerId()).orElse(null);
                if(owner == null){
                    throw new Exception("owner not found");
                }
                if(!owner.getUsername().equals(cookingRecipe.getCustomer().getUsername())){
                    throw new Exception("you can't remove share this recipe because you don't own it");
                }
                cookingRecipe.getUserShares().remove(userSystem);
            }else if(object.getTypeInteract().equals(TypeInteract.VOTE.toString())){
                votesRepository.deleteById(VotesKey.builder().cookingRecipeId(object.getCookingRecipeId()).customerId(object.getCustomerId()).build());
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
