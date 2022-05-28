package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.*;
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

    private final CustomerService cs;
    private final RestaurantService rs;
    private final ReservationService res;
    private final MailingService ms;

    @Autowired
    public CustReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView userHistory(@ModelAttribute("reservationForm") final ReservationForm form,
                                    final Principal principal){

        ModelAndView mav = new ModelAndView("customerViews/reservation/history");
        rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        List<Reservation> reservations = res.getReservationsByCustomer(customer);

        mav.addObject("reservations", reservations);
        mav.addObject("customer", customer);
        mav.addObject("progressBarNumber", customer.getPoints());
        return mav;
    }
    @RequestMapping(value = "/active-reservations", method = RequestMethod.GET)
    public ModelAndView activeReservations(final Principal principal){
        ModelAndView mav = new ModelAndView("customerViews/reservation/CustomerActiveReservations");
        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        List<Reservation> reservations = res.getReservationsByCustomerAndActive(customer);

        mav.addObject("reservations", reservations);
        mav.addObject("customer", customer);
        return mav;
    }


    @RequestMapping(value = "/createReservation-0", method = RequestMethod.POST)
    public ModelAndView createReservation_0_POST() {

        return new ModelAndView("redirect:/createReservation-1");
    }

    @RequestMapping(value = "/createReservation-1")
    public ModelAndView createReservation_1(@ModelAttribute("qPeopleForm") final NumberForm form) {

        return new ModelAndView("customerViews/reservation/createReservation_1_people");
    }

    @RequestMapping(value = "/createReservation-1", method = RequestMethod.POST)
    public ModelAndView createReservation_1_POST(@Valid @ModelAttribute("qPeopleForm") final NumberForm form,
                                                 final BindingResult errors){
        if (errors.hasErrors()){
            return createReservation_1(form);
        }
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer maybeCustomer = cs.getCustomerById(1).orElseThrow(CustomerNotFoundException::new);

        Reservation reservation = res.createReservation(restaurant, maybeCustomer, 0, Integer.parseInt(form.getNumber()));
        res.updateReservationStatus(reservation, ReservationStatus.MAYBE_RESERVATION);


        return new ModelAndView("redirect:/createReservation-3/" + reservation.getId());
    }

    @RequestMapping(value = "/createReservation-2/{reservationId}")
    public ModelAndView createReservation_2(@PathVariable("reservationId") final String reservationIdP,
                                            @ModelAttribute("ReservationForm") final ReservationForm form) {
        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        return new ModelAndView("customerViews/reservation/createReservation_2_date");
    }

    @RequestMapping(value = "/createReservation-2/{reservationId}", method = RequestMethod.POST)
    public ModelAndView createReservation_2_POST(@PathVariable("reservationId") final String reservationIdP,
                                                 @Valid @ModelAttribute("reservationForm") final ReservationForm form,
                                                 final BindingResult errors){
        if (errors.hasErrors()){
            //return createReservation_2(form);
        }
        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        /*
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer maybeCustomer = cs.getCustomerById(1).orElseThrow(CustomerNotFoundException::new);

        Reservation reservation = res.createReservation(restaurant, maybeCustomer, 0, Integer.parseInt(form.getNumber()));
        res.updateReservationStatus(reservation, ReservationStatus.MAYBE_RESERVATION);
         */


        return new ModelAndView("redirect:/createReservation-3/" + reservation.getId());
    }


    @RequestMapping(value = "/createReservation-3/{reservationId}")
    public ModelAndView createReservation_3(@PathVariable("reservationId") final String reservationIdP,
                                            @Valid @ModelAttribute("hourForm") final NumberForm form) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_3_time");
        List<Integer> hours = res.getAvailableHours(reservation.getRestaurant().getId(), reservation.getqPeople());

        mav.addObject("hours", hours);
        mav.addObject("people", reservation.getqPeople());
        return mav;
    }

    @RequestMapping(value = "/createReservation-3/{reservationId}", method = RequestMethod.POST)
    public ModelAndView createReservation_3_POST(@PathVariable("reservationId") final String reservationIdP,
                                                 @Valid @ModelAttribute("hourForm") final NumberForm form,
                                                 final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return createReservation_3(reservationIdP, form);
        }
        ControllerUtils.longParser(reservationIdP, form.getNumber()).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long hour = Long.parseLong(form.getNumber());


        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        Customer maybeCustomer = new Customer(1, "", "", "", 0);
        res.updateReservationById(reservation, maybeCustomer, hour, reservation.getqPeople());

        return new ModelAndView("redirect:/createReservation-4/" + reservationId);

    }

    @RequestMapping(value = "/createReservation-4/{reservationId}", method = RequestMethod.GET)
    public ModelAndView createReservation_4(@PathVariable("reservationId") final String reservationIdP,
                                            @ModelAttribute("reservationForm") final ReservationForm form) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_4_user");
        mav.addObject("reservation", reservation);
        return mav;
    }

    @RequestMapping(value = "/createReservation-4/{reservationId}", method = RequestMethod.POST)
    public ModelAndView createReservation_4(@PathVariable("reservationId") final String reservationIdP,
                                            @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {
        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        if (errors.hasErrors()){
            return createReservation_4(reservationIdP, form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());


        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        res.updateReservationById(reservation, customer, reservation.getReservationHour(), reservation.getqPeople());
        res.updateReservationStatus(reservation, ReservationStatus.OPEN);


        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer,reservation);


        return new ModelAndView("redirect:/notify/" + reservationId);
    }

    @RequestMapping(value = "/confirmReservation/{reservationId}", method = RequestMethod.GET)
    public ModelAndView confirmReservation(@PathVariable("reservationId") final String reservationIdP,
                                           final Principal principal,
                                           @ModelAttribute("confirmReservationForm") final ReservationForm form) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        form.setName(customer.getCustomerName());
        form.setPhone(customer.getPhone());
        form.setMail(customer.getMail());

        final ModelAndView mav = new ModelAndView("customerViews/reservation/confirmReservation");

        mav.addObject("reservation", reservation);
        mav.addObject("customer", customer);
        return mav;
    }

    @RequestMapping(value = "/confirmReservation/{reservationId}", method = RequestMethod.POST)
    public ModelAndView confirmReservationPost( @PathVariable("reservationId") final String reservationIdP,
                                                Principal principal,
                                                @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return confirmReservation(reservationIdP, principal, form);
        }
        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);
        ms.sendConfirmationEmail(restaurant, customer, reservation);

        res.updateReservationById(reservation, customer, reservation.getReservationHour(), reservation.getqPeople());
        res.updateReservationStatus(reservation, ReservationStatus.OPEN);

        return new ModelAndView("redirect:/notify/" + reservationId);
    }

    @RequestMapping(value = "/findReservation", method = RequestMethod.GET)
    public ModelAndView findReservation(@ModelAttribute("findReservationForm") final FindReservationForm form) {

        return new ModelAndView("customerViews/reservation/findReservation");
    }
    @RequestMapping(value = "/findReservation", method = RequestMethod.POST)
    public ModelAndView findReservationForm(@ModelAttribute("findReservationForm") final FindReservationForm form,
                                            final BindingResult errors) {

        if (errors.hasErrors()) {
            return findReservation(form);
        }
        Reservation reservation = res.getReservationByIdAndIsActive(form.getReservationId()).orElseThrow(ReservationNotFoundException::new);
        return new ModelAndView("redirect:/menu?reservationId=" + reservation.getId());
    }

    @RequestMapping("/notify/{reservationId}")
    public ModelAndView notifyCustomer(@PathVariable("reservationId") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("customerViews/reservation/notifyCustomer");

        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);


        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("reservation", reservation);

        return mav;
    }

    @RequestMapping("/reservation-cancel" )
    public ModelAndView cancelReservation(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                                          @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);

        final ModelAndView mav = new ModelAndView("customerViews/reservation/cancelReservation");
        mav.addObject("restaurant", restaurant);
        mav.addObject("reservationId", reservationId);

        return mav;
    }

    @RequestMapping(value = "/reservation-cancel", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        ControllerUtils.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        ms.sendCancellationEmail(restaurant,customer,reservation);

        res.updateReservationStatus(reservation, ReservationStatus.CANCELED);
        return new ModelAndView("redirect:/");
    }
}
