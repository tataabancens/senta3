package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.OrderItemNotFoundException;
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
public class RestOrderController {
    private final ReservationService res;
    private final RestaurantService rs;


    @Autowired
    public RestOrderController(final ReservationService res, final RestaurantService rs) {
        this.res = res;
        this.rs = rs;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders", method = RequestMethod.GET)
    public ModelAndView restaurantOrders(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                             @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/order/orders");
        List<Reservation> reservations = res.getReservationsSeated(restaurant);
        List<OrderItem> incomingItems = res.getOrderItemsByStatus(OrderItemStatus.INCOMING);
        List<OrderItem> finishedItems = res.getOrderItemsByStatus(OrderItemStatus.FINISHED);

        mav.addObject("reservations", reservations);
        mav.addObject("incomingItems", incomingItems);
        mav.addObject("finishedItems", finishedItems);
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/incomingToFinished-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusFinished (@PathVariable("restaurantId") final String restaurantIdP,
                                                 @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        ControllerUtils.longParser(orderItemIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        OrderItem orderItem = res.getOrderItemById(orderItemId).orElseThrow(OrderItemNotFoundException::new);
        res.updateOrderItemStatus(orderItem, OrderItemStatus.FINISHED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/finishedToDelivered-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusDelivered (@PathVariable("restaurantId") final String restaurantIdP,
                                                  @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        ControllerUtils.longParser(orderItemIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        OrderItem orderItem = res.getOrderItemById(orderItemId).orElseThrow(OrderItemNotFoundException::new);

        res.updateOrderItemStatus(orderItem, OrderItemStatus.DELIVERED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/waiter/finishedToDelivered-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusDeliveredWaiter (@PathVariable("restaurantId") final String restaurantIdP,
                                                  @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        ControllerUtils.longParser(orderItemIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        OrderItem orderItem = res.getOrderItemById(orderItemId).orElseThrow(OrderItemNotFoundException::new);

        res.updateOrderItemStatus(orderItem, OrderItemStatus.DELIVERED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/waiter");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/waiter")
    public ModelAndView waiterOrders(@PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        List<Reservation> reservations = res.getReservationsSeated(restaurant);
        List<OrderItem> finishedItems = res.getOrderItemsByStatus(OrderItemStatus.FINISHED);

        final ModelAndView mav = new ModelAndView("restaurantViews/order/waiterView");

        mav.addObject("reservations", reservations);
        mav.addObject("finishedItems", finishedItems);
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/waiter/lowerHand-{reservationSecurityCode}")
    public ModelAndView waiterLowerHand(@PathVariable("restaurantId") final String restaurantIdP,
                                        @PathVariable("reservationSecurityCode") final String reservationSecurityCodeP) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);

        res.raiseHand(reservationSecurityCodeP);
        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/waiter");

    }
}
