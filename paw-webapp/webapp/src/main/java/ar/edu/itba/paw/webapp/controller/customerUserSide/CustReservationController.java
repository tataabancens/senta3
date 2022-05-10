package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
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

        ModelAndView mav = new ModelAndView("customerViews/reservation/history");
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        List<FullReservation> reservations = res.getReservationsByCustomerId(customer.getCustomerId());

        mav.addObject("reservations", reservations);
        mav.addObject("customer", customer);
        mav.addObject("progressBarNumber", customer.getPoints());
        return mav;
    }
    @RequestMapping(value = "/active-reservations", method = RequestMethod.GET)
    public ModelAndView activeReservations(Principal principal){
        ModelAndView mav = new ModelAndView("customerViews/reservation/CustomerActiveReservations");
        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        List<FullReservation> reservations = res.getReservationsByCustomerIdAndActive(customer.getCustomerId());

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

        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_1_people");
        return mav;
    }

    @RequestMapping(value = "/createReservation-1", method = RequestMethod.POST)
    public ModelAndView createReservation_1_POST(@Valid @ModelAttribute("qPeopleForm") final NumberForm form,
                                                 final BindingResult errors){
        if (errors.hasErrors()){
            return createReservation_1(form);
        }
        Reservation reservation = res.createReservation(1, 1, 0, Integer.parseInt(form.getNumber()));
        res.updateReservationStatus(reservation.getReservationId(), ReservationStatus.MAYBE_RESERVATION);


        return new ModelAndView("redirect:/createReservation-2/" + reservation.getReservationId());
    }

    @RequestMapping(value = "/createReservation-2/{reservationId}")
    public ModelAndView createReservation_2(@PathVariable("reservationId") final String reservationIdP,
                                            @Valid @ModelAttribute("hourForm") final NumberForm form) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_3_time");

        mav.addObject("hours", res.getAvailableHours(1, reservation.getqPeople()));
        mav.addObject("people", reservation.getqPeople());
        return mav;
    }

    @RequestMapping(value = "/createReservation-2/{reservationId}", method = RequestMethod.POST)
    public ModelAndView createReservation_2_POST(@PathVariable("reservationId") final String reservationIdP,
                                                 @Valid @ModelAttribute("hourForm") final NumberForm form,
                                                 final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return createReservation_2(reservationIdP, form);
        }
        controllerService.longParser(reservationIdP, form.getNumber()).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long hour = Long.parseLong(form.getNumber());


        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        res.updateReservationById(reservationId, 1, hour, reservation.getqPeople());

        return new ModelAndView("redirect:/createReservation-3/" + reservationId + "/redirect");

    }

    @RequestMapping(value = "/createReservation-3/{reservationId}", method = RequestMethod.GET)
    public ModelAndView createReservation_3(@PathVariable("reservationId") final String reservationIdP,
                                            @ModelAttribute("reservationForm") final ReservationForm form) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_4_user");
        mav.addObject("reservation", reservation);
        return mav;
    }

    @RequestMapping(value = "/createReservation-3/{reservationId}", method = RequestMethod.POST)
    public ModelAndView createReservation_3(@PathVariable("reservationId") final String reservationIdP,
                                            @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {
        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        if (errors.hasErrors()){
            return createReservation_3(reservationIdP, form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());


        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        res.updateReservationById(reservationId, customer.getCustomerId(), reservation.getReservationHour(), reservation.getqPeople());
        res.updateReservationStatus(reservationId, ReservationStatus.OPEN);


        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
                customer,reservation);


        return new ModelAndView("redirect:/notify/" + reservationId);
    }

    @RequestMapping(value = "/confirmReservation/{reservationId}", method = RequestMethod.GET)
    public ModelAndView confirmReservation(@PathVariable("reservationId") final String reservationIdP,
                                           Principal principal,
                                           @ModelAttribute("confirmReservationForm") final ReservationForm form) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
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
        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndStatus(reservationId, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        ms.sendConfirmationEmail(restaurant, customer, reservation);

        res.updateReservationById(reservationId, customer.getCustomerId(), reservation.getReservationHour(), reservation.getqPeople());
        res.updateReservationStatus(reservationId, ReservationStatus.OPEN);

        return new ModelAndView("redirect:/notify/" + reservationId);
    }

    @RequestMapping(value = "/findReservation", method = RequestMethod.GET)
    public ModelAndView findReservation(@ModelAttribute("findReservationForm") final FindReservationForm form) {

        final ModelAndView mav = new ModelAndView("customerViews/reservation/findReservation");
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

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
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

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);

        final ModelAndView mav = new ModelAndView("customerViews/reservation/cancelReservation");
        mav.addObject("restaurant", restaurant);
        mav.addObject("reservationId", reservationId);

        return mav;
    }

    @RequestMapping(value = "/reservation-cancel", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirm(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP) throws Exception {

        controllerService.longParser(reservationIdP).orElseThrow(() -> new LongParseException(reservationIdP));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        ms.sendCancellationEmail(restaurant,customer,reservation);

        res.updateReservationStatus(reservationId, ReservationStatus.CANCELED);
        return new ModelAndView("redirect:/");
    }
}
