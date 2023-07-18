package ar.edu.itba.paw.webapp.form.customvalidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CategoryValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryConstraint {

    public String message() default "Esta categoria ya existe";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

}
