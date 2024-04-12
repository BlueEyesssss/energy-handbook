package com.khaphp.energyhandbook.Util.ValidData.StatusOrder;

import com.khaphp.energyhandbook.Util.ValidData.StatusRecipe.ValidStatusRecipeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidStatusOrderValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatusOrder {
    String message() default "Invalid status, must be PENDING, ACCEPT, REJECT, CANCEL, WAITING, FINISH";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
