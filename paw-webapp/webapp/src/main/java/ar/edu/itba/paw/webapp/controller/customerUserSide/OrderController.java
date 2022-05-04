package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
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
    private RestaurantService rs;
    private ReservationService res;
    private  DishService ds;
    private CustomerService cs;
    private MailingService ms;
    private ControllerService controllerService;


    @Autowired
    public OrderController(final RestaurantService rs, final ReservationService res, final DishService ds, final CustomerService cs, final MailingService ms, ControllerService controllerService) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
        this.cs = cs;
        this.ms = ms;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.GET)
    public ModelAndView addOrderItem(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "dishId", defaultValue = "1") final String dishIdP,
                                  @ModelAttribute("orderForm") final OrderForm form) throws Exception {

        controllerService.longParser(reservationIdP, dishIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("order/orderItem");

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("reservationId", reservationId);

        return mav;
    }
    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.POST)
    public ModelAndView addOrderItemForm(@RequestParam(name = "reservationId") final String reservationIdP,
                                            @RequestParam(name = "dishId") final String dishIdP,
                                            @Valid @ModelAttribute("orderForm") final OrderForm form,
                                            final BindingResult errors) throws Exception {
        controllerService.longParser(reservationIdP, dishIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long dishId = Long.parseLong(dishIdP);

        if (errors.hasErrors()) {
            return addOrderItem(reservationIdP, dishIdP, form);
        }
        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);

        res.createOrderItemByReservationId(reservationId, dish, form.getOrderItem().getQuantity());
        //Agregar a bd

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping("/order")
    public ModelAndView orderFood(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("order/order");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);

        List<FullOrderItem> orderItems = res.getOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        return mav;
    }

    @RequestMapping("/order/send-food")
    public ModelAndView orderFoodSend(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                  @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        List<FullOrderItem> orderItems = res.getOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);


        final ModelAndView mav = new ModelAndView("order/completeOrder");
        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);

        return mav;
    }
    @RequestMapping(value = "/order/send-food", method = RequestMethod.POST)
    public ModelAndView orderFoodConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                      @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<FullOrderItem> orderItems = res.getOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        new Thread(() -> {
            ms.sendOrderEmail(restaurant, customer, orderItems);
        }).start();

        res.updateOrderItemsStatus(reservationId, OrderItemStatus.SELECTED, OrderItemStatus.ORDERED);

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping("/order/send-receipt")
    public ModelAndView orderReceipt(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                      @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<FullOrderItem> orderItems = res.getAllOrderItemsByReservationId(reservationId);
        boolean canOrderReceipt = res.canOrderReceipt(reservation, orderItems.size() > 0);

        final ModelAndView mav = new ModelAndView("order/receipt");
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

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);
        List<FullOrderItem> orderItems = res.getAllOrderItemsByReservationId(reservationId);

        cs.addPointsToCustomer(customer.getCustomerId(), res.getTotal(orderItems));

        new Thread(() -> {
            ms.sendReceiptEmail(restaurant, customer);
        }).start();

        res.updateReservationStatus(reservationId, ReservationStatus.CHECK_ORDERED);
        res.updateOrderItemsStatus(reservationId, OrderItemStatus.ORDERED, OrderItemStatus.CHECK_ORDERED);

        return new ModelAndView("redirect:/order/send-receipt/confirmed?restaurantId=" + restaurantId + "&points=" + cs.getPoints(res.getTotal(orderItems)));
    }

    @RequestMapping(value = "/order/send-receipt/confirmed")
    public ModelAndView orderReceiptConfirmed(@RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP,
                                              @RequestParam(name = "points") final String pointsP) throws Exception {

        controllerService.longParser(restaurantIdP, pointsP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("order/receiptConfirmed");
        mav.addObject("restaurant", restaurant);
        mav.addObject("points", pointsP);

        return mav;
    }

    @RequestMapping(value= "/order/empty-cart", method = RequestMethod.POST)
    public ModelAndView emptyCart(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        res.deleteOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping(value= "/order/remove-dish", method = RequestMethod.POST)
    public ModelAndView deleteOrderItemFromCart(@RequestParam(name = "orderItemId") final String orderItemIdP,
                                  @RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);
        controllerService.longParser(orderItemIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        //res.deleteOrderItemsByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED);
        res.deleteOrderItemByReservationIdAndStatus(reservationId, OrderItemStatus.SELECTED, orderItemId);

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }
}
