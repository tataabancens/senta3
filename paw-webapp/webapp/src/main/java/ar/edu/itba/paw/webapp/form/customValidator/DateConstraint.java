package ar.edu.itba.paw.webapp.form.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
    public String message() default "La reserva no puede ser en el pasado";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
