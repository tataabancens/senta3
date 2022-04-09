package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute("reservationForm") final ReservationForm form){
        return new ModelAndView("/register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView checkout(@Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors,
                                 @RequestParam(name = "userId", defaultValue = "1") final long userId) {
        if (errors.hasErrors()){
            return createForm(form);
        }
        final ModelAndView mav = new ModelAndView("register");

        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));
        mav.addObject("dish", ds.getDishById(1).orElseThrow(DishNotFoundException::new));
        mav.addObject("reservation", reservationService.getReservationById(1).orElseThrow(ReservationNotFoundException::new));
        return mav;
    }

    @RequestMapping("/menu")
    public ModelAndView menuPage(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("old/menu");

        mav.addObject("dish", rs.getRestaurantDishes(1));
        return mav;
    }

    @RequestMapping("/order")
    public ModelAndView orderFood(@RequestParam(name = "userId", defaultValue = "1") final long userId) {

        final ModelAndView mav = new ModelAndView("order");

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
