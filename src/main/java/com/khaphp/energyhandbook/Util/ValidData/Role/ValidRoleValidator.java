package com.khaphp.energyhandbook.Util.ValidData.Role;

import com.khaphp.energyhandbook.Constant.Gender;
import com.khaphp.energyhandbook.Constant.Role;
import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidRoleValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> roles = List.of(Role.SHIPPER.toString(), Role.CUSTOMER.toString(), Role.EMPLOYEE.toString());
        if(roles.contains(value)){
            return true;
        }else{
            return false;
        }
    }
}
