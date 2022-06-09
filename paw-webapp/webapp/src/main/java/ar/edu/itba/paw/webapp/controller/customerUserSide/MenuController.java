package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final RestaurantService rs;
    private final ReservationService res;
    private final CustomerService cs;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    public MenuController(final RestaurantService rs, final ReservationService res, final CustomerService cs) {
        this.rs = rs;
        this.res = res;
        this.cs = cs;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld(@RequestParam(name = "category", defaultValue = "2") final String categoryIdP ) {

        final ModelAndView mav = new ModelAndView("customerViews/menu/menu");
        ControllerUtils.longParser(categoryIdP).orElseThrow(DishCategoryNotFoundException::new);
        long categoryId = Long.parseLong(categoryIdP);
        LOGGER.info("Attempting to show menu");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryById(categoryId).orElseThrow(DishCategoryNotFoundException::new);

        // deprecated List<Dish> dishes = rs.getRestaurantDishesByCategory(1, DishCategory.valueOf(category));
        //restaurant.setDishes(dishes);

        mav.addObject("restaurant", restaurant);
        mav.addObject("dishes", restaurant.getDishesByCategory(dishCategory));
        mav.addObject("categories", restaurant.getDishCategories());
        mav.addObject("currentCategory", dishCategory);

        return mav;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationSecurityCode", defaultValue = "1") final String reservationSecurityCode,
                             @RequestParam(name = "category", defaultValue = "2") final String categoryIdP) throws Exception {

        ControllerUtils.longParser(categoryIdP).orElseThrow(DishCategoryNotFoundException::new);
        long categoryId = Long.parseLong(categoryIdP);

        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryById(categoryId).orElseThrow(DishCategoryNotFoundException::new);
//        deprecated List<Dish> dishes = rs.getRestaurantDishesByCategory(1, DishCategory.valueOf(category));
//        restaurant.setDishes(dishes);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        List<OrderItem> orderedItems = res.getOrderItemsByReservationAndOrder(reservation);
        List<OrderItem> orderItems = res.getOrderItemsByReservationAndStatus(reservation, OrderItemStatus.SELECTED);
        List<OrderItem> incomingItems = res.getOrderItemsByReservationAndStatus(reservation, OrderItemStatus.ORDERED);
        List<OrderItem> oldItems = res.getOrderItemsByReservationAndStatus(reservation, OrderItemStatus.DELIVERED);

        boolean canOrderReceipt = res.canOrderReceipt(reservation, orderedItems.size() > 0);
        boolean canCancelReservation = reservation.getReservationStatus() == ReservationStatus.OPEN;

        final ModelAndView mav = new ModelAndView("customerViews/menu/fullMenu");
        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservation.getId()));
        mav.addObject("restaurant", restaurant);
        mav.addObject("dishes", restaurant.getDishesByCategory(dishCategory));
        mav.addObject("customer", customer);
        mav.addObject("categories", restaurant.getDishCategories());
        mav.addObject("currentCategory", dishCategory);

        mav.addObject("reservation", reservation);


        mav.addObject("orderItems", orderItems);
        mav.addObject("selected", orderItems.size());
        mav.addObject("total", res.getTotal(orderItems));

        mav.addObject("oldItems", oldItems);
        mav.addObject("oldItemsSize", oldItems.size());
        mav.addObject("totalOld", res.getTotal(oldItems));

        mav.addObject("incomingItems", incomingItems);
        mav.addObject("incomingItemsSize", incomingItems.size());
        mav.addObject("totalIncoming", res.getTotal(incomingItems));

        mav.addObject("canOrderReceipt", canOrderReceipt);
        mav.addObject("canCancelReservation", canCancelReservation);

        mav.addObject("unavailable", res.getUnavailableItems(reservation.getId()));

        return mav;
    }

    @RequestMapping(value= "/menu/applyDiscount/{reservationId}", method = RequestMethod.POST)
    public ModelAndView applyDiscount(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        res.applyDiscount(reservationId);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping(value= "/menu/cancelDiscount/{reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelDiscount(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        res.cancelDiscount(reservationId);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

}
