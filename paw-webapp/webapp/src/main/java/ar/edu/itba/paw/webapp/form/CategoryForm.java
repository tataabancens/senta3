package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.CategoryConstraint;

import javax.validation.constraints.Size;

public class CategoryForm {

    @CategoryConstraint
    @Size(min = 6, max = 50)
    String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
