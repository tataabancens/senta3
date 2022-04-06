package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

@Controller
public class HelloWorldController {

//    private UserService us;
    private RestaurantService rs;
    private DishService ds;
    private ReservationService reservationService;
    private CustomerService cs;
    private MailingService ms;

    @Autowired
    public HelloWorldController(final RestaurantService rs, final DishService ds,
                                final ReservationService reservationService, final CustomerService cs, final MailingService ms) {
        this.us = us;
        this.rs = rs;
        this.ds = ds;
        this.ms = ms;
        this.cs = cs;
        this.reservationService = reservationService;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("index");

//        mav.addObject("user", us.getUserByID(userId).orElseThrow(UserNotFoundException::new));
        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));
        mav.addObject("dish", ds.getDishById(1).orElseThrow(DishNotFoundException::new));
        mav.addObject("reservation", reservationService.getReservationById(1).orElseThrow(ReservationNotFoundException::new));
        return mav;
    }

    @RequestMapping("/menu")
    public ModelAndView menuPage(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("menu");

        mav.addObject("user", us.getUserByID(userId).orElseThrow(UserNotFoundException::new));
        mav.addObject("dish", rs.getRestaurantDishes(1));
        return mav;
    }

    @RequestMapping("/order")
    public ModelAndView orderFood(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("order");

//        mav.addObject("user", us.getUserByID(userId).orElseThrow(UserNotFoundException::new));
        mav.addObject("dish", rs.getRestaurantDishes(1));
        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                cs.getUserByID(1).orElseThrow(CustomerNotFoundException::new));
        return mav;
    }

    @RequestMapping("/profile/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {

        final ModelAndView mav = new ModelAndView("profile");

//        mav.addObject("user", us.getUserByID(userId).orElseThrow(UserNotFoundException::new));
        return mav;
    }

    @RequestMapping("/chau")
    public ModelAndView goodbyeWorld() {
        final ModelAndView mav = new ModelAndView("byebye");

//        mav.addObject("user", us.getUserByID(1).orElseThrow(UserNotFoundException::new));
        return mav;
    }
}
