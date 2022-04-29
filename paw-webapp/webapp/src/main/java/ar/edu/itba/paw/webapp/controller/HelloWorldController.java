package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private RestaurantService rs;
    private DishService ds;
    private ReservationService reservationService;
    private CustomerService cs;
    private MailingService ms;

    @Autowired
    public HelloWorldController(final RestaurantService rs, final DishService ds,
                                final ReservationService reservationService, final CustomerService cs, final MailingService ms) {
        this.rs = rs;
        this.ds = ds;
        this.ms = ms;
        this.cs = cs;
        this.reservationService = reservationService;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("menu/menu");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);
        return mav;
    }


    @RequestMapping("/profile/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {

        final ModelAndView mav = new ModelAndView("aa_Trash/profile");

//        mav.addObject("user", us.getUserByID(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/chau")
    public ModelAndView goodbyeWorld() {
        final ModelAndView mav = new ModelAndView("aa_Trash/byebye");

//        mav.addObject("user", us.getUserByID(1).orElseThrow(UserNotFoundException::new));
        return mav;
    }
/*
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }*/
}
