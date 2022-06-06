package ar.edu.itba.paw.webapp.form.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderConstraint {
    public String message() default "La cantidad de productos debe ser entre 1 y 50";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
