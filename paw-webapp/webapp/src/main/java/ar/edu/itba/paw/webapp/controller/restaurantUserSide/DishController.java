package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.CategoryForm;
import ar.edu.itba.paw.webapp.form.DeleteCategoryForm;
import ar.edu.itba.paw.webapp.form.EditDishForm;
import ar.edu.itba.paw.webapp.form.customValidator.DeleteCategoryConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.Valid;

@Controller
public class DishController {

    private final RestaurantService rs;
    private final DishService ds;
    private final ImageService ims;

    @Autowired
    public DishController(final DishService ds, final ImageService ims, final RestaurantService rs) {
        this.ds = ds;
        this.ims = ims;
        this.rs = rs;
    }
}
