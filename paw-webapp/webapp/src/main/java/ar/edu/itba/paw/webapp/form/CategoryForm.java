package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.CategoryConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryForm {

    @CategoryConstraint
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    @Size(min = 3, max = 20)
    String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
