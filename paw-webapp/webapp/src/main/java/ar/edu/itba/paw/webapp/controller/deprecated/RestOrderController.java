//package ar.edu.itba.paw.webapp.controller.deprecated;
//
//import ar.edu.itba.paw.model.OrderItem;
//import ar.edu.itba.paw.model.Reservation;
//import ar.edu.itba.paw.model.Restaurant;
//import ar.edu.itba.paw.model.enums.OrderItemStatus;
//import ar.edu.itba.paw.service.*;
//import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
//import ar.edu.itba.paw.model.exceptions.LongParseException;
//import ar.edu.itba.paw.model.exceptions.OrderItemNotFoundException;
//import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//public class RestOrderController {
//    private final ReservationService res;
//    private final RestaurantService rs;
//
//
//    @Autowired
//    public RestOrderController(final ReservationService res, final RestaurantService rs) {
//        this.res = res;
//        this.rs = rs;
//    }
//}
