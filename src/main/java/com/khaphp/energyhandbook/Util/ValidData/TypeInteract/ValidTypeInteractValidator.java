package com.khaphp.energyhandbook.Util.ValidData.TypeInteract;

import com.khaphp.energyhandbook.Constant.StatusCookingRecipe;
import com.khaphp.energyhandbook.Constant.TypeInteract;
import com.khaphp.energyhandbook.Util.ValidData.StatusRecipe.ValidStatusReceipe;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidTypeInteractValidator implements ConstraintValidator<ValidTypeInteract, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(TypeInteract.LIKE.toString(), TypeInteract.SHARE.toString(), TypeInteract.VOTE.toString(), TypeInteract.REPORT.toString(), StatusCookingRecipe.PRIVATE.toString(), StatusCookingRecipe.BAN.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
