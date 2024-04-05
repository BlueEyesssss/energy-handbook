package com.khaphp.energyhandbook.Util.ValidData.Role;

import com.khaphp.energyhandbook.Util.ValidData.Gender.ValidGenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidRoleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Invalid Gender, must be SHIPPER/ CUSTOMER/ EMPLOYEE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
