package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.TableNumberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RestReservationController {

    private final CustomerService cs;
    private final RestaurantService rs;
    private final ReservationService res;
    private final MailingService ms;

    @Autowired
    public RestReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
    }
}
