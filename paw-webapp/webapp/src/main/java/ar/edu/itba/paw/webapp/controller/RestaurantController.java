package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RestaurantController {
    RestaurantService rs;
    ReservationService res;

    @Autowired
    public RestaurantController(RestaurantService rs) {
        this.rs = rs;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("index");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);
        return mav;
    }

    
    @RequestMapping("/menu")
    public ModelAndView menuPage(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("fullMenu");

        mav.addObject("dish", rs.getRestaurantDishes(1));
        return mav;
    }



/*
    @RequestMapping("/{reservationId}")
    public ModelAndView helloWorld(@PathVariable("reservationId") final int reservationId) {

        final ModelAndView mav = new ModelAndView("fullMenu ");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));

        Optional<Reservation> reservation = res.getReservationById(reservationId);

        mav.addObject("restaurant", restaurant);
        mav.addObject("reservation", reservation);
        return mav;
    }
    */
}
