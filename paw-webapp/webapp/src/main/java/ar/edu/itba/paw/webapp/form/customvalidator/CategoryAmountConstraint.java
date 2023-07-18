package ar.edu.itba.paw.webapp.form.customvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryAmountValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryAmountConstraint {
    public String message() default "No se pueden crear mas de 10 categorias";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
