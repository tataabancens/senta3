package ar.edu.itba.paw.webapp.form.customValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OpenCloseHoursValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenCloseHoursConstraint {
    String message() default "No puede abir antes de cerrar!";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

    String openHour();

    String closeHour();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        OpenCloseHoursConstraint[] value();
    }
}
