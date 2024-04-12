package com.khaphp.energyhandbook.Util.ValidData.StatusOrder;

import com.khaphp.energyhandbook.Constant.StatusCookingRecipe;
import com.khaphp.energyhandbook.Constant.StatusOrder;
import com.khaphp.energyhandbook.Util.ValidData.StatusRecipe.ValidStatusReceipe;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidStatusOrderValidator implements ConstraintValidator<ValidStatusOrder, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(StatusOrder.PENDING.toString(), StatusOrder.ACCEPT.toString(), StatusOrder.REJECT.toString(), StatusOrder.CANCEL.toString(), StatusOrder.WAITING.toString(), StatusOrder.FINISH.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
