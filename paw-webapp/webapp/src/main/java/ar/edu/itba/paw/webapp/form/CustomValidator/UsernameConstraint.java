package ar.edu.itba.paw.webapp.form.CustomValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = UsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    public String message() default "Nombre de usuario duplicado";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
