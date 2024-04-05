package com.khaphp.energyhandbook.Util.ValidData.Birthday;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;

public class ValidBirthdayValidator implements ConstraintValidator<ValidBirthday, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == 0) {
            return false;
        }

        Date currentDate = new Date(System.currentTimeMillis());
        long curentTime = currentDate.getTime() / 1000;
        return value < curentTime;
    }
}
