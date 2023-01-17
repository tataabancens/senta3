package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ar.edu.itba.paw.webapp.form.OrderForm;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    private final RestaurantService rs;
    private final ReservationService res;
    private final DishService ds;
    private final CustomerService cs;


    @Autowired
    public OrderController(final RestaurantService rs, final ReservationService res, final DishService ds, final CustomerService cs) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
        this.cs = cs;
    }
}
