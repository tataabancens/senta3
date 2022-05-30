package ar.edu.itba.paw.webapp.form.CustomValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateValidator implements ConstraintValidator<DateConstraint,String> {

    @Override
    public void initialize(DateConstraint dateConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.compareTo("") == 0)
            return false;

        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append(" 00:00:00.00001");
        Timestamp input = Timestamp.valueOf(stringBuilder.toString());
        Timestamp now = Timestamp.from(Instant.now());

        return input.after(now);
    }
}
