package ar.edu.itba.paw.webapp.form.customvalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = QPeopleValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QPeopleConstraint {
    public String message() default "La cantidad de personas no puede exceder la capacidad máxima del restaurant";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
