package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MenuController {
    private RestaurantService rs;
    private ReservationService res;
    private ControllerService controllerService;
    private CustomerService cs;


    @Autowired
    public MenuController(final RestaurantService rs, final ReservationService res,
                          final ControllerService controllerService, final CustomerService cs) {
        this.rs = rs;
        this.res = res;
        this.controllerService = controllerService;
        this.cs = cs;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld() {

        final ModelAndView mav = new ModelAndView("customerViews/menu/menu");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);
        mav.addObject("categories", DishCategory.getAsList());
        return mav;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                             @RequestParam(name = "category", defaultValue = "MAIN_DISH") final String category) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("customerViews/menu/fullMenu");
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        List<Dish> dishes = rs.getRestaurantDishesByCategory(1, DishCategory.valueOf(category));
        restaurant.setDishes(dishes);

        //Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);


        List<FullOrderItem> orderedItems = res.getOrderItemsByReservationIdAndOrder(reservationId);
        List<FullOrderItem> orderItems = res.getOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);

        boolean canOrderReceipt = res.canOrderReceipt(reservation, orderedItems.size() > 0);

        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("restaurant", restaurant);
//        mav.addObject("dish", dishes);
        mav.addObject("customer", customer);
        mav.addObject("categories", DishCategory.getAsList());
        mav.addObject("currentCategory", DishCategory.valueOf(category));

        mav.addObject("reservation", reservation);


        mav.addObject("orderItems", orderItems);
        mav.addObject("selected", orderItems.size());
        mav.addObject("total", res.getTotal(orderItems));


        mav.addObject("canOrderReceipt", canOrderReceipt);

        mav.addObject("unavailable", res.getUnavailableItems(reservationId));

        return mav;
    }

    @RequestMapping(value= "/menu/applyDiscount/{reservationId}", method = RequestMethod.POST)
    public ModelAndView applyDiscount(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        res.applyDiscount(reservationId);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping(value= "/menu/cancelDiscount/{reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelDiscount(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        res.cancelDiscount(reservationId);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

}
