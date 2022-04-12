package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.FindReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.webapp.form.OrderForm;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    private RestaurantService rs;
    private ReservationService res;
    private  DishService ds;


    @Autowired
    public OrderController(final RestaurantService rs, final ReservationService res, final DishService ds) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId){
        final ModelAndView mav = new ModelAndView("fullMenu");
        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);


        mav.addObject("restaurant", restaurant);
        mav.addObject("dish", rs.getRestaurantDishes(1));

        mav.addObject("reservation", reservation);

        List<FullOrderItem> orderItems = res.getOrderItemsByReservationId(reservationId);
        mav.addObject("orderItems", orderItems);
        return mav;
    }

    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.GET)
    public ModelAndView orderItem(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                                  @RequestParam(name = "dishId", defaultValue = "1") final long dishId,
                                  @ModelAttribute("orderForm") final OrderForm form){
        final ModelAndView mav = new ModelAndView("orderItem");

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("reservationId", reservationId);

        return mav;
    }
    @RequestMapping(value = "/menu/orderItem", method = RequestMethod.POST)
    public ModelAndView findReservationForm(@RequestParam(name = "reservationId") final long reservationId,
                                            @RequestParam(name = "dishId") final long dishId,@ModelAttribute("orderForm") final OrderForm form,
                                            final BindingResult errors) {

        if (errors.hasErrors()) {
            return orderItem(form.getOrderItem().getReservationId(), form.getOrderItem().getDishId(), form);
        }
        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);

        res.createOrderItemByReservationId(reservationId, dish, form.getOrderItem().getQuantity());
        //Agregar a bd

        return new ModelAndView("redirect:/menu?reservationId=" + reservationId);
    }

    @RequestMapping("/order")
    public ModelAndView orderFood(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                                  @RequestParam(name = "restaurantId", defaultValue = "1") final long restaurantId) {

        final ModelAndView mav = new ModelAndView("order");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        List<FullOrderItem> orderItems = res.getOrderItemsByReservationId(reservationId);
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        return mav;
    }

}
