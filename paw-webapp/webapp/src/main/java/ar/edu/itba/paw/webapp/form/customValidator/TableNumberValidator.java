package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class TableNumberValidator implements ConstraintValidator<TableNumberConstraint, String> {

    @Autowired
    private ReservationService res;

    @Autowired
    private RestaurantService rs;

    @Override
    public void initialize(TableNumberConstraint tableNumberConstraint) {

    }

    @Override
    public boolean isValid(String tableNumber, ConstraintValidatorContext constraintValidatorContext) {
        List<Reservation> resList = res.getReservationsSeated(rs.getRestaurantById(1).get());
        for(Reservation reservation : resList){
            if (Objects.equals(String.valueOf(reservation.getTableNumber()), tableNumber)){
                return false;
            }
        }
        return true;
    }
}
