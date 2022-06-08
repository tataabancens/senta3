package ar.edu.itba.paw.webapp.form.customValidator;

import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CategoryValidator implements ConstraintValidator<CategoryConstraint, String>{

    @Autowired
    private RestaurantService rs;

    @Override
    public void initialize(CategoryConstraint categoryConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<DishCategory> maybeCategory= rs.getDishCategoryByName(s);
        return !maybeCategory.isPresent();
    }

}
