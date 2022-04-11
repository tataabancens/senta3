package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
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

@Controller
public class OrderController {
    private RestaurantService rs;
    private ReservationService res;
    //private OrderService os;


    @Autowired
    public OrderController(RestaurantService rs, ReservationService res) {
        //this.os = os;
        this.rs = rs;
        this.res = res;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                                   @ModelAttribute("orderForm") final OrderForm form){
        final ModelAndView mav = new ModelAndView("fullMenu");

        form.setReservationId(reservationId);
        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);


        mav.addObject("restaurant", restaurant);
        mav.addObject("dish", rs.getRestaurantDishes(1));

        mav.addObject("reservation", reservation);
        return mav;
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ModelAndView menuForm(@Valid @ModelAttribute("orderForm") final OrderForm form, final BindingResult errors) {
        if (errors.hasErrors()){
            return menu(form.getReservationId(), form);
        }

/*
        Customer customer=cs.create(form.getName(), form.getPhone(), form.getMail());
        Reservation reservation = res.createReservation(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer,form.getTimeStamp());


        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer);

 */

        final ModelAndView mav = new ModelAndView("order");

        //mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));



        return mav;
    }
}
