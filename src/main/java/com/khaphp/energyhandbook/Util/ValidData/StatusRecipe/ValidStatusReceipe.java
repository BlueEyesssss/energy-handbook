package com.khaphp.energyhandbook.Util.ValidData.StatusRecipe;

import com.khaphp.energyhandbook.Util.ValidData.Status.ValidStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidStatusRecipeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatusReceipe {
    String message() default "Invalid status, must be PUBLIC, PRIVATE, BAN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
