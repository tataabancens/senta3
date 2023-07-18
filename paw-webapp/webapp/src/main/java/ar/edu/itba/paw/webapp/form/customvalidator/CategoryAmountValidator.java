package ar.edu.itba.paw.webapp.form.customvalidator;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CategoryAmountValidator implements ConstraintValidator<CategoryAmountConstraint, String> {

    @Autowired
    private RestaurantService rs;

    @Override
    public void initialize(CategoryAmountConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Restaurant> restaurant = rs.getRestaurantById(1);
        return restaurant.filter(restaurant1 -> restaurant1.getDishCategories().size() <= 10).isPresent();
    }
}
