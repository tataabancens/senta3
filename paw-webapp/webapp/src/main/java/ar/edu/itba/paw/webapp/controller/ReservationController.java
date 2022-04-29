package ar.edu.itba.paw.webapp.controller;

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
import java.util.List;

@Controller
public class ReservationController {
    private CustomerService cs;
    private RestaurantService rs;
    private ReservationService res;
    private MailingService ms;
    private ControllerService controllerService;

    @Autowired
    public ReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms, ControllerService controllerService) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/createReservation", method = RequestMethod.GET)
    public ModelAndView createForm(@ModelAttribute("reservationForm") final ReservationForm form){

        ModelAndView mav = new ModelAndView("reservation/createReservation_old");
        mav.addObject("hours", res.getAvailableHours(1));

        return mav;
    }

    @RequestMapping(value = "/createReservation", method = RequestMethod.POST)
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

    @RequestMapping(value = "/createReservation-1", method = RequestMethod.GET)
    public ModelAndView createReservation_1(@ModelAttribute("peopleForm") final NumberForm form){
        ModelAndView mav = new ModelAndView("reservation/createReservation_1_people");
        return mav;
    }

    @RequestMapping(value = "/createReservation-1", method = RequestMethod.POST)
    public ModelAndView createReservation_1(@Valid @ModelAttribute("peopleForm") final NumberForm form, final BindingResult errors) {
        /*
        if (errors.hasErrors()){
            return createForm(form);
        }
         */

        final ModelAndView mav = new ModelAndView("reservation/createReservation_2_date");

        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));


        mav.addObject("dates", new int[]{ 1,2,3,4,5,6,7,8,9,10 } ); //acá iría un get dates o algo así


        return mav;
    }

    @RequestMapping(value = "/createReservation-2", method = RequestMethod.GET)
    public ModelAndView createReservation_2(@ModelAttribute("dayForm") final NumberForm form){
        ModelAndView mav = new ModelAndView("reservation/createReservation_2_date");
        return mav;
    }

    @RequestMapping(value = "/createReservation-2", method = RequestMethod.POST)
    public ModelAndView createReservation_2(@Valid @ModelAttribute("dayForm") final NumberForm form, final BindingResult errors) {
        /*
        if (errors.hasErrors()){
            return createForm(form);
        }
         */

        final ModelAndView mav = new ModelAndView("reservation/createReservation_2_date");

        mav.addObject("restaurant", rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new));


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
        Reservation reservation = res.getReservationById(form.getReservationId()).orElseThrow(ReservationNotFoundException::new);
        return new ModelAndView("redirect:/menu?reservationId=" + reservation.getReservationId());
    }

    @RequestMapping("/notify/{reservationId}")
    public ModelAndView notifyCustomer(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("reservation/notifyCustomer");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);


        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("reservation", reservation);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.GET)
    public ModelAndView cancelReservationConfirmation(@PathVariable("reservationId") final String reservationIdP,
                                                      @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(reservationIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("reservation/cancelReservationConfirmation");
        mav.addObject("reservationId", reservationId);
        mav.addObject("restaurantId", restaurantId);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirmationPost(@PathVariable("reservationId") final String reservationIdP,
                                                          @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(reservationIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        res.updateReservationStatus(reservationId, ReservationStatus.CANCELED);
        ms.sendCancellationEmail(restaurant,customer,reservation);
        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/menu");
    }



    @RequestMapping("/reservation-cancel" )
    public ModelAndView cancelReservation(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                          @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        controllerService.longParser(reservationIdP, restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);

        final ModelAndView mav = new ModelAndView("reservation/cancelReservation");
        mav.addObject("restaurant", restaurant);
        mav.addObject("reservationId", reservationId);

        return mav;
    }

    @RequestMapping(value = "/reservation-cancel", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        ms.sendCancellationEmail(restaurant,customer,reservation);
        res.updateReservationStatus(reservationId, ReservationStatus.CANCELED);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations")
    public ModelAndView reservations(@PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("reservation/reservations");
        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservations(restaurantId);
        mav.addObject("reservations", reservations);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/seatCustomer={reservationId}")
    public ModelAndView seatCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.updateReservationStatus(reservationId, ReservationStatus.SEATED);

        final ModelAndView mav = new ModelAndView("reservation/reservations");
        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservations(restaurantId);
        mav.addObject("reservations", reservations);
        return mav;
    }


}
