package ar.edu.itba.paw.webapp.form.CustomValidator;

import ar.edu.itba.paw.model.OrderItem;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<OrderConstraint, OrderItem> {
    @Override
    public void initialize(OrderConstraint orderConstraint) {

    }

    @Override
    public boolean isValid(OrderItem orderItem, ConstraintValidatorContext constraintValidatorContext) {
        if (orderItem==null)
            return false;

        return orderItem.getQuantity()>0;
    }
}
