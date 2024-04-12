package com.khaphp.energyhandbook.Util.ValidData.MethodOrder;

import com.khaphp.energyhandbook.Util.ValidData.Role.ValidRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidMethodValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMethod {
    String message() default "Invalid Gender, must be COD, WALLET, THIRDPARTY";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
