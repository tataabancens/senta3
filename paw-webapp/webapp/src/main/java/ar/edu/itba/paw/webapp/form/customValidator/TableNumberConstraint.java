package ar.edu.itba.paw.webapp.form.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = TableNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableNumberConstraint {
    public String message() default "Mesa ya en uso";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
