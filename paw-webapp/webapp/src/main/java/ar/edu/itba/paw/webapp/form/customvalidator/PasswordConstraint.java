package ar.edu.itba.paw.webapp.form.customvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    public String message() default "Las contrasenias no coinciden";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
