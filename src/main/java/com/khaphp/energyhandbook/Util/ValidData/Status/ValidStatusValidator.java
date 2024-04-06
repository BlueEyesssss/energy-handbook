package com.khaphp.energyhandbook.Util.ValidData.Status;

import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Constant.Status;
import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidStatusValidator implements ConstraintValidator<ValidStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(Status.ACTIVE.toString(), Status.DEACTIVE.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
