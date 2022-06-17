package ar.edu.itba.paw.webapp.form.customValidator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OpenCloseHoursValidator implements ConstraintValidator<OpenCloseHoursConstraint, Object> {

    private String openHour;
    private String closeHour;

    public void initialize(OpenCloseHoursConstraint constraintAnnotation) {
        this.openHour = constraintAnnotation.openHour();
        this.closeHour = constraintAnnotation.closeHour();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {

        Object openHourValue = new BeanWrapperImpl(value)
                .getPropertyValue(openHour);
        Object closeHourValue = new BeanWrapperImpl(value)
                .getPropertyValue(closeHour);

        if (openHourValue != null) {
            return Integer.parseInt(openHourValue.toString()) < Integer.parseInt(closeHourValue.toString());
        }
        return false;
    }
}
