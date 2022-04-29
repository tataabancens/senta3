package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.ReservationStatus;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.FindReservationForm;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private CustomerService cs;
    private RestaurantService rs;
    private ReservationService res;
    private MailingService ms;

    @Autowired
    public RegisterController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute("reservationForm") final ReservationForm form){

        ModelAndView mav = new ModelAndView("reservation/register");
        mav.addObject("hours", res.getAvailableHours(1));

        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView checkout(@Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {
        if (errors.hasErrors()){
            return createForm(form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());
        Reservation reservation = res.createReservation(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer, form.getHour());


        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer,reservation);

        final ModelAndView mav = new ModelAndView("reservation/notifyCustomer");

        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));

        mav.addObject("reservation", reservation);


        return mav;
    }
    @RequestMapping(value = "/findReservation", method = RequestMethod.GET)
    public ModelAndView findReservation(@ModelAttribute("findReservationForm") final FindReservationForm form) {

        final ModelAndView mav = new ModelAndView("reservation/findReservation");
        return mav;
    }
    @RequestMapping(value = "/findReservation", method = RequestMethod.POST)
    public ModelAndView findReservationForm(@ModelAttribute("findReservationForm") final FindReservationForm form,
                                            final BindingResult errors) {

        if (errors.hasErrors()) {
            return findReservation(form);
        }
        Reservation reservation = res.getReservationByIdAndStatus(form.getReservationId(), ReservationStatus.ACTIVE).orElseThrow(ReservationNotFoundException::new);
        return new ModelAndView("redirect:/menu?reservationId=" + reservation.getReservationId());
    }

    @RequestMapping("/notify/{reservationId}")
    public ModelAndView notifyCustomer(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("reservation/notifyCustomer");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);


        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("reservation", reservation);

        return mav;
    }

    private void longParser(Object... str) throws Exception {
        if(str.length > 0){
            try{
                Long str0 = Long.parseLong((String) str[0]);
            } catch (NumberFormatException e) {
                throw new Exception(str[0] + " is not a number");
            }
        }
        if(str.length > 1){
            try{
                Long str1 = Long.parseLong((String) str[1]);
            } catch (NumberFormatException e) {
                throw new Exception(str[1] + " is not a number");
            }
        }
        if(str.length > 2){
            try{
                Long str2 = Long.parseLong((String) str[2]);
            } catch (NumberFormatException e) {
                throw new Exception(str[2] + " is not a number");
            }
        }
    }
}
