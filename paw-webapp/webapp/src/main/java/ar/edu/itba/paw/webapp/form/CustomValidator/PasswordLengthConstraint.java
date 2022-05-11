package ar.edu.itba.paw.webapp.form.CustomValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordLengthConstraint {
    public String message() default "La contrasenia debe tener mas de 8 caracteres";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
