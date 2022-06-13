package ar.edu.itba.paw.webapp.form.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDateTime;

public class DateValidator implements ConstraintValidator<DateConstraint,String> {

    @Override
    public void initialize(DateConstraint dateConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.compareTo("") == 0)
            return false;

        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append(" 23:59:59.99999");
        LocalDateTime input = LocalDateTime.parse(stringBuilder.toString());
        LocalDateTime now = LocalDateTime.from(Instant.now());

        return input.isAfter(now);
    }
}
