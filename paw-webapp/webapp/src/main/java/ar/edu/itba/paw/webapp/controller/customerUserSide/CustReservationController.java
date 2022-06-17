package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<Reservation> reservations = res.getReservationsByCustomerAndStatus(customer, ReservationStatus.FINISHED);


        mav.addObject("reservations", reservations);
        mav.addObject("customer", customer);
        mav.addObject("progressBarNumber", customer.getPoints());
        return mav;
    }

    @RequestMapping(value = "/history/reservation", method = RequestMethod.GET)
    public ModelAndView addOrderItemForm(@RequestParam(name = "reservationSecurityCode") final String reservationSecurityCode){

        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);

        List<OrderItem> items = res.getAllOrderItemsByReservation(reservation);


        ModelAndView mav = new ModelAndView("customerViews/reservation/reservationHistory");
        mav.addObject(items);
        mav.addObject(reservation);
        mav.addObject("total",res.getTotal(items));
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
    public ModelAndView createReservation_1(@ModelAttribute("qPeopleForm") final qPeopleForm form) {

        return new ModelAndView("customerViews/reservation/createReservation_1_people");
    }

    @RequestMapping(value = "/createReservation-1", method = RequestMethod.POST)
    public ModelAndView createReservation_1_POST(@Valid @ModelAttribute("qPeopleForm") final qPeopleForm form,
                                                 final BindingResult errors){
        if (errors.hasErrors()){
            return createReservation_1(form);
        }
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        Customer maybeCustomer = cs.getCustomerById(1).orElseThrow(CustomerNotFoundException::new);

        Reservation reservation = res.createReservation(restaurant, maybeCustomer, 0, Integer.parseInt(form.getNumber()));
        res.setReservationSecurityCode(reservation);
        res.updateReservationStatus(reservation, ReservationStatus.MAYBE_RESERVATION);


        return new ModelAndView("redirect:/createReservation-2/" + reservation.getSecurityCode());
    }

    @RequestMapping(value = "/createReservation-2/{reservationSecurityCode}")
    public ModelAndView createReservation_2(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                            @ModelAttribute("dateForm") final DateForm form) {

        return new ModelAndView("customerViews/reservation/createReservation_2_date");
    }

    @RequestMapping(value = "/createReservation-2/{reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView createReservation_2_POST(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                 @Valid @ModelAttribute("dateForm") final DateForm form,
                                                 final BindingResult errors){
        if (errors.hasErrors()){
            return createReservation_2(reservationSecurityCode, form);
        }

        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        ControllerUtils.timestampParser(form.getDate()).orElseThrow(() -> new LongParseException("" + reservation.getId())); //actúa como un isPresent
        LocalDateTime reservationDate = ControllerUtils.timestampParser(form.getDate()).get();
        res.updateReservationDateById(reservation, reservationDate);

        return new ModelAndView("redirect:/createReservation-3/" + reservation.getSecurityCode());
    }

    @RequestMapping(value = "/createReservation-3/{reservationSecurityCode}")
    public ModelAndView createReservation_3(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                            @Valid @ModelAttribute("hourForm") final NumberForm form) throws Exception {

        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_3_time");
        List<Integer> hours = res.getAvailableHours(reservation.getRestaurant().getId(), reservation.getqPeople(), reservation.getReservationDate());

        mav.addObject("hours", hours);
        mav.addObject("people", reservation.getqPeople());
        return mav;
    }

    @RequestMapping(value = "/createReservation-3/{reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView createReservation_3_POST(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                 @Valid @ModelAttribute("hourForm") final NumberForm form,
                                                 final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return createReservation_3(reservationSecurityCode, form);
        }

        long hour = Long.parseLong(form.getNumber());


        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        Customer maybeCustomer = new Customer(1, "", "", "", 0);
        res.updateReservationById(reservation, maybeCustomer, hour, reservation.getqPeople());

        return new ModelAndView("redirect:/createReservation-3/" + reservation.getSecurityCode() + "/redirect");

    }

    @RequestMapping(value = "/createReservation-4/{reservationSecurityCode}", method = RequestMethod.GET)
    public ModelAndView createReservation_4(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                            @ModelAttribute("reservationForm") final ReservationForm form) throws Exception {

        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        ModelAndView mav = new ModelAndView("customerViews/reservation/createReservation_4_user");
        mav.addObject("reservation", reservation);
        return mav;
    }

    @RequestMapping(value = "/createReservation-4/{reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView createReservation_4(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                            @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {

        if (errors.hasErrors()){
            return createReservation_4(reservationSecurityCode, form);
        }

        Customer customer = cs.create(form.getName(), form.getPhone(), form.getMail());
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);

        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
//        res.updateReservationById(reservation, customer, reservation.getReservationHour(), reservation.getqPeople());
//        res.updateReservationStatus(reservation, ReservationStatus.OPEN);
//        ms.sendConfirmationEmail(rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new),
//                customer,reservation);

        res.finishReservation(restaurant, customer, reservation);

        return new ModelAndView("redirect:/notify/" + reservationSecurityCode);
    }

    @RequestMapping(value = "/confirmReservation/{reservationSecurityCode}", method = RequestMethod.GET) //cuando crea una reserva un usuario registrado
    public ModelAndView confirmReservation(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                           final Principal principal,
                                           @ModelAttribute("confirmReservationForm") final ReservationForm form) throws Exception {

        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);

        form.setName(customer.getCustomerName());
        form.setPhone(customer.getPhone());
        form.setMail(customer.getMail());

        final ModelAndView mav = new ModelAndView("customerViews/reservation/confirmReservation");

        mav.addObject("reservation", reservation);
        mav.addObject("customer", customer);
        mav.addObject("isRepeating", res.isRepeating(customer, reservation));
        return mav;
    }

    @RequestMapping(value = "/confirmReservation/{reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView confirmReservationPost( @PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                Principal principal,
                                                @Valid @ModelAttribute("reservationForm") final ReservationForm form, final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return confirmReservation(reservationSecurityCode, principal, form);
        }

        Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
        Reservation reservation = res.getReservationBySecurityCodeAndStatus(reservationSecurityCode, ReservationStatus.MAYBE_RESERVATION).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);

        //ms.sendConfirmationEmail(restaurant, customer, reservation);
        //res.updateReservationById(reservation, customer, reservation.getReservationHour(), reservation.getqPeople());
        //res.updateReservationStatus(reservation, ReservationStatus.OPEN);
        res.finishReservation(restaurant, customer, reservation);

        return new ModelAndView("redirect:/notify/" + reservationSecurityCode);
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
        Reservation reservation = res.getReservationByIdAndIsActive(form.getSecurityCode()).orElseThrow(ReservationNotFoundException::new);
        return new ModelAndView("redirect:/menu?reservationSecurityCode=" + reservation.getSecurityCode());
    }

    @RequestMapping("/notify/{reservationSecurityCode}")
    public ModelAndView notifyCustomer(@PathVariable("reservationSecurityCode") final String reservationSecurityCode) throws Exception {

        final ModelAndView mav = new ModelAndView("customerViews/reservation/notifyCustomer");

        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        Reservation reservation = res.getReservationByIdAndIsActive(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        mav.addObject("reservation", reservation);

        return mav;
    }

    @RequestMapping("/reservation-cancel" )
    public ModelAndView cancelReservation(@RequestParam(name = "reservationSecurityCode", defaultValue = "1") final String reservationSecurityCode,
                                          @RequestParam(name = "restaurantId", defaultValue = "1") final String restaurantIdP) throws Exception {

        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        res.getReservationByIdAndIsActive(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);

        final ModelAndView mav = new ModelAndView("customerViews/reservation/cancelReservation");
        mav.addObject("restaurant", restaurant);
        mav.addObject("reservationSecurityCode", reservationSecurityCode);

        return mav;
    }

    @RequestMapping(value = "/reservation-cancel", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirm(@RequestParam(name = "reservationSecurityCode", defaultValue = "1") final String reservationSecurityCode) throws Exception {

        Reservation reservation = res.getReservationByIdAndIsActive(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

//        ms.sendCancellationEmail(restaurant,customer,reservation);
//        res.updateReservationStatus(reservation, ReservationStatus.CANCELED);
        res.cancelReservation(restaurant, customer, reservation);

        return new ModelAndView("redirect:/");
    }
}
