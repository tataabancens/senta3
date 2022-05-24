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
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.webapp.form.OrderForm;


import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.GET)
    public ModelAndView addOrderItem(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "dishId", defaultValue = "1") final String dishIdP,
                                     @RequestParam(name = "isFromOrder", defaultValue = "false") final String isFromOrderP,
                                  @ModelAttribute("orderForm") final OrderForm form) throws Exception {

        ControllerUtils.longParser(reservationIdP, dishIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("customerViews/order/orderItem");

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        boolean isFromOrder = res.isFromOrder(isFromOrderP);

        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("reservationId", reservationId);
        mav.addObject("isFromOrder", isFromOrder);

        return mav;
    }
    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.POST)
    public ModelAndView addOrderItemForm(@RequestParam(name = "reservationId") final String reservationIdP,
                                            @RequestParam(name = "dishId") final String dishIdP,
                                         @RequestParam(name = "isFromOrder", defaultValue = "false") final String isFromOrderP,
                                            @Valid @ModelAttribute("orderForm") final OrderForm form,
                                            final BindingResult errors) throws Exception {
        ControllerUtils.longParser(reservationIdP, dishIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long dishId = Long.parseLong(dishIdP);

        if (errors.hasErrors()) {
            return addOrderItem(reservationIdP, dishIdP, isFromOrderP, form);
        }
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        boolean isFromOrder = res.isFromOrder(isFromOrderP);
        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);

        res.createOrderItemByReservation(reservation, dish, form.getOrderItem().getQuantity());

        if (isFromOrder) {
            return new ModelAndView("redirect:/order/send-food?reservationId=" + reservationId + "&restaurantId=" + reservation.getRestaurant().getId());
        }
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping("/order")
    public ModelAndView orderFood(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("customerViews/order/order");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);

        List<OrderItem> orderItems = res.getOrderItemsByReservationAndStatus(reservation, OrderItemStatus.SELECTED);
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        return mav;
    }

    @RequestMapping("/order/send-food")
    public ModelAndView orderFoodSend(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        List<OrderItem> orderItems = res.getOrderItemsByReservationAndStatus(reservation, OrderItemStatus.SELECTED);

        Dish recommendedDish = ds.getRecommendedDish(reservationId);
        boolean isPresent = ds.isPresent(recommendedDish);

        final ModelAndView mav = new ModelAndView("customerViews/order/completeOrder");
        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservation", reservation);
        mav.addObject("isPresent", isPresent);
        mav.addObject("recommendedDish", recommendedDish);

        return mav;
    }
    @RequestMapping(value = "/order/send-food", method = RequestMethod.POST)
    public ModelAndView orderFoodConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                      @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);

        res.updateOrderItemsStatus(reservationId, OrderItemStatus.SELECTED, OrderItemStatus.ORDERED);

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping("/order/send-receipt")
    public ModelAndView orderReceipt(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                      @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);
        boolean canOrderReceipt = res.canOrderReceipt(reservation, orderItems.size() > 0);

        final ModelAndView mav = new ModelAndView("restaurantViews/order/receipt");
        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        mav.addObject("canOrderReceipt", canOrderReceipt);

        return mav;
    }
    @RequestMapping(value = "/order/send-receipt", method = RequestMethod.POST)
    public ModelAndView orderReceiptSend(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                     @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);

        cs.addPointsToCustomer(customer, res.getTotal(orderItems));

        res.updateReservationStatus(reservation, ReservationStatus.CHECK_ORDERED);
        res.updateOrderItemsStatus(reservationId, OrderItemStatus.ORDERED, OrderItemStatus.CHECK_ORDERED);

        return new ModelAndView("redirect:/order/send-receipt/confirmed?restaurantId=" + restaurantId + "&points=" + cs.getPoints(res.getTotal(orderItems)));
    }

    @RequestMapping(value = "/order/send-receipt/confirmed")
    public ModelAndView orderReceiptConfirmed(@RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP,
                                              @RequestParam(name = "points") final String pointsP) throws Exception {

        ControllerUtils.longParser(restaurantIdP, pointsP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("customerViews/order/receiptConfirmed");
        mav.addObject("restaurant", restaurant);
        mav.addObject("points", pointsP);

        return mav;
    }

    @RequestMapping(value= "/order/empty-cart", method = RequestMethod.POST)
    public ModelAndView emptyCart(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        res.deleteOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping(value= "/order/remove-dish", method = RequestMethod.POST)
    public ModelAndView deleteOrderItemFromCart(@RequestParam(name = "orderItemId") final String orderItemIdP,
                                  @RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);
        ControllerUtils.longParser(orderItemIdP).orElseThrow(() -> new LongParseException(orderItemIdP));
        long orderItemId = Long.parseLong(orderItemIdP);

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        //res.deleteOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        res.deleteOrderItemByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED, orderItemId);

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }
}
