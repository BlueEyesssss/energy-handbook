package com.khaphp.energyhandbook.Util.ValidData.Status;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidStatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStatus {
    String message() default "Invalid status, must be ACTIVE/ DEACTIVE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
