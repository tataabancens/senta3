package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ModelAndView createForm(@ModelAttribute("orderForm") final OrderForm form){
        return new ModelAndView("/fullMenu");
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public ModelAndView checkout(@Valid @ModelAttribute("orderForm") final OrderForm form, final BindingResult errors) {
        if (errors.hasErrors()){
            return createForm(form);
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
