package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.FindReservationForm;
import ar.edu.itba.paw.webapp.form.NumberForm;
import ar.edu.itba.paw.webapp.form.ReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class CustReservationController {

    private CustomerService cs;
    private RestaurantService rs;
    private ReservationService res;
    private MailingService ms;
    private ControllerService controllerService;

    @Autowired
    public CustReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms, ControllerService controllerService) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
        this.controllerService = controllerService;
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView userHistory(@ModelAttribute("reservationForm") final ReservationForm form,
                                    Principal principal){

        ModelAndView mav = new ModelAndView("reservation/history");
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        List<FullReservation> reservations = res.getReservationsByCustomerId(customer.getCustomerId());

        mav.addObject("reservations", reservations);
        mav.addObject("customer", customer);
        mav.addObject("progressBarNumber",200);
        return mav;
    }
    /*
    @RequestMapping(value = "/createReservation", method = RequestMethod.POST)
    public ModelAndView checkout(@Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) {
        if (errors.hasErrors()){
            return createForm(form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());
        Reservation reservation = res.createReservation(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer, form.getHour(), 2);


        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer,reservation);

        final ModelAndView mav = new ModelAndView("reservation/notifyCustomer");

        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));

        mav.addObject("reservation", reservation);


        return mav;
    }

     */


    @RequestMapping(value = "/createReservation-1")
    public ModelAndView createReservation_1(){
        ModelAndView mav = new ModelAndView("reservation/createReservation_1_people");
        return mav;
    }

    @RequestMapping(value = "/createReservation-1/people={qPeople}")
    public ModelAndView createReservation_2(@PathVariable("qPeople") final String qPeopleP) throws Exception {

        controllerService.longParser(qPeopleP);
        long qPeople = Long.parseLong(qPeopleP);
        ModelAndView mav = new ModelAndView("reservation/createReservation_3_time");
        mav.addObject("hours", res.getAvailableHours(1, qPeople));
        mav.addObject("people", qPeople);
        return mav;
    }

    @RequestMapping(value = "/createReservation-1/people={qPeople}/hour={hour}", method = RequestMethod.GET)
    public ModelAndView createReservation_3(@PathVariable("qPeople") final String qPeopleP,
                                            @PathVariable("hour") final String hourP,
                                            @ModelAttribute("reservationForm") final ReservationForm form) throws Exception {

        controllerService.longParser(qPeopleP);
        long qPeople = Long.parseLong(qPeopleP);
        controllerService.longParser(hourP);
        long hour = Long.parseLong(hourP);

        ModelAndView mav = new ModelAndView("reservation/createReservation_4_user");
        mav.addObject("hour", hour);
        mav.addObject("people", qPeople);
        form.setqPeople((int) qPeople);
        form.setHour((int) hour);
        return mav;
    }

    @RequestMapping(value = "/createReservation-1/people={qPeople}/hour={hour}", method = RequestMethod.POST)
    public ModelAndView createReservation_3(@PathVariable("qPeople") final String qPeopleP,
                                            @PathVariable("hour") final String hourP,
                                            @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {
        controllerService.longParser(qPeopleP);
        long qPeople = Long.parseLong(qPeopleP);
        controllerService.longParser(hourP);
        long hour = Long.parseLong(hourP);

        if (errors.hasErrors()){
            return createReservation_3(qPeopleP, hourP, form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());
        Reservation reservation = res.createReservation(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer, (int) hour, (int) qPeople);


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
        Reservation reservation = res.getReservationByIdAndIsActive(form.getReservationId()).orElseThrow(ReservationNotFoundException::new);
        return new ModelAndView("redirect:/menu?reservationId=" + reservation.getReservationId());
    }

    @RequestMapping("/notify/{reservationId}")
    public ModelAndView notifyCustomer(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("reservation/notifyCustomer");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);


        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("reservation", reservation);

        return mav;
    }

    @RequestMapping("/reservation-cancel" )
    public ModelAndView cancelReservation(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                          @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);

        final ModelAndView mav = new ModelAndView("reservation/cancelReservation");
        mav.addObject("restaurant", restaurant);
        mav.addObject("reservationId", reservationId);

        return mav;
    }

    @RequestMapping(value = "/reservation-cancel", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        ms.sendCancellationEmail(restaurant,customer,reservation);
        res.updateReservationStatus(reservationId, ReservationStatus.CANCELED);
        return new ModelAndView("redirect:/");
    }
}
