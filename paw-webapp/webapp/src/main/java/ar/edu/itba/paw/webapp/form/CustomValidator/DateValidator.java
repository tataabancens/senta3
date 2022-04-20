package ar.edu.itba.paw.webapp.form.CustomValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
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
        Timestamp now = Timestamp.from(Instant.now());
        Timestamp oneYear = Timestamp.from(Instant.now());
        Calendar cal = Calendar.getInstance();
        cal.setTime(oneYear);
        cal.add(Calendar.YEAR, 1);
        oneYear.setTime(cal.getTime().getTime());
        return input.compareTo(now)>0 && input.compareTo(oneYear)<0;
    }
}
