package ar.edu.itba.paw.webapp.form.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<DateConstraint,String> {

    @Override
    public void initialize(DateConstraint dateConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.compareTo("") == 0)
            return false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str = s + " 23:59:59";
        LocalDateTime time = LocalDateTime.parse(str, formatter);
        LocalDateTime now = LocalDateTime.now();

        return time.isAfter(now) && time.isBefore(now.plusYears(1));
        //return true;
    }
}
