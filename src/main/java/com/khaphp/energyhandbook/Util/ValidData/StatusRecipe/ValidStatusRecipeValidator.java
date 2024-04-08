package com.khaphp.energyhandbook.Util.ValidData.StatusRecipe;

import com.khaphp.energyhandbook.Constant.StatusCookingRecipe;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidStatusRecipeValidator implements ConstraintValidator<ValidStatusReceipe, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(StatusCookingRecipe.PUBLIC.toString(), StatusCookingRecipe.PRIVATE.toString(), StatusCookingRecipe.BAN.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
