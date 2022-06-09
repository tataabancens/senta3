package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class qPeopleValidator implements ConstraintValidator<qPeopleConstraint, String> {

    @Autowired
    private RestaurantService rs;

    @Override
    public void initialize(qPeopleConstraint qPeopleConstraint) {

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
