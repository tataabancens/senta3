package ar.edu.itba.paw.webapp.form.customvalidator;

import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QPeopleValidator implements ConstraintValidator<QPeopleConstraint, String> {

    @Autowired
    private RestaurantService rs;

    @Override
    public void initialize(QPeopleConstraint qPeopleConstraint) {

    }

    @Override
    public boolean isValid(String qPeople, ConstraintValidatorContext constraintValidatorContext) {
        if(ControllerUtils.longParser(qPeople).isPresent()){
            if(Integer.parseInt(qPeople) > rs.getRestaurantById(1).get().getTotalChairs()) {
                return false;
            }
        }
        return true;
    }
}
