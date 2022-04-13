package ar.edu.itba.paw.webapp.form.CustomValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

public class DateValidator implements ConstraintValidator<DateConstraint,String> {

    @Override
    public void initialize(DateConstraint dateConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.compareTo("")==0)
            return false;
        Timestamp input=Timestamp.valueOf(LocalDateTime.parse(s));
        Timestamp ts = Timestamp.from(Instant.now());
        return input.compareTo(ts)>0;
    }
}
