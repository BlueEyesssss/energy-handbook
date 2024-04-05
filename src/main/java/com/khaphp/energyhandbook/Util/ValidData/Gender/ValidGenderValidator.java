package com.khaphp.energyhandbook.Util.ValidData.Gender;

import com.khaphp.energyhandbook.Constant.Gender;
import com.khaphp.energyhandbook.Util.ValidData.Birthday.ValidBirthday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;
import java.util.List;

public class ValidGenderValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> genders = List.of(Gender.MALE.toString(), Gender.FEMALE.toString());
        if(genders.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
