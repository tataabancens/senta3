package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.FullOrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.OrderItemStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
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

    @Autowired
    public RestOrderController(ReservationService res) {
        this.res = res;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders", method = RequestMethod.GET)
    public ModelAndView restaurantOrders(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                             @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/order/orders");
        List<Reservation> reservations = res.getReservationsSeated(restaurantId);

        for (Reservation reservation : reservations) {
            res.updateOrderItemsStatus(reservation.getId(), OrderItemStatus.ORDERED, OrderItemStatus.INCOMING);
        }
        List<FullOrderItem> incomingItems = res.getOrderItemsByStatus(OrderItemStatus.INCOMING);
        List<FullOrderItem> finishedItems = res.getOrderItemsByStatus(OrderItemStatus.FINISHED);

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

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.FINISHED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/finishedToDelivered-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusDelivered (@PathVariable("restaurantId") final String restaurantIdP,
                                                  @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        ControllerUtils.longParser(orderItemIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.DELIVERED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

}
