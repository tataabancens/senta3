package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.EditPhoneForm;
import ar.edu.itba.paw.webapp.form.EditEmailForm;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import ar.edu.itba.paw.webapp.form.EditTablesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class RestController {
    private final RestaurantService rs;

    @Autowired
    public RestController(RestaurantService rs) {
        this.rs = rs;
    }
}
