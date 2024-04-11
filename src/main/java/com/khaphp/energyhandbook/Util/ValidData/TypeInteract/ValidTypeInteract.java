package com.khaphp.energyhandbook.Util.ValidData.TypeInteract;

import com.khaphp.energyhandbook.Util.ValidData.StatusRecipe.ValidStatusRecipeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidTypeInteractValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTypeInteract {
    String message() default "Invalid status, must be LIKE, SHARE, VOTE, REPORT";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
