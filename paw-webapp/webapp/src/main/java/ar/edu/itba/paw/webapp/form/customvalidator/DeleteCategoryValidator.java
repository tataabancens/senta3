package ar.edu.itba.paw.webapp.form.customvalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DeleteCategoryValidator  implements ConstraintValidator<DeleteCategoryConstraint, Long> {

    @Override
    public void initialize(DeleteCategoryConstraint deleteCategoryConstraint) {

    }

    @Override
    public boolean isValid(Long categoryId, ConstraintValidatorContext constraintValidatorContext) {
        return categoryId != 1;
    }
}
