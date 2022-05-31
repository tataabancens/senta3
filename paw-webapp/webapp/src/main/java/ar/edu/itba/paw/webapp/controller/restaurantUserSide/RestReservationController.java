package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.NumberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RestReservationController {

    private final CustomerService cs;
    private final RestaurantService rs;
    private final ReservationService res;
    private final MailingService ms;

    @Autowired
    public RestReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.GET)
    public ModelAndView cancelReservationConfirmation(@PathVariable("reservationId") final String reservationIdP,
                                                      @PathVariable("restaurantId") final String restaurantIdP,
                                                      @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                      @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                      @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                                      @RequestParam(value = "page", defaultValue = "1") final String page) throws Exception {
        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/cancelReservationConfirmation");
        mav.addObject("reservationId", reservationId);
        mav.addObject("restaurantId", restaurantId);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("filterStatus", filterStatus);
        mav.addObject("page", Integer.parseInt(page));

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirmationPost(@PathVariable("reservationId") final String reservationIdP,
                                                          @PathVariable("restaurantId") final String restaurantIdP,
                                                          @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                          @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                          @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                                          @RequestParam(value = "page", defaultValue = "1") final String page) throws Exception {
        ControllerUtils.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.CANCELED);

        ms.sendCancellationEmail(restaurant,customer,reservation);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations") //?orderBy=String
    public ModelAndView reservationsOrderBy(@PathVariable("restaurantId") final String restaurantIdP,
                                            @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                            @RequestParam(value = "filterStatus", defaultValue = "0") final String filterStatus,
                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                            @ModelAttribute("filterForm") final FilterForm filterForm,
                                            @ModelAttribute("seatForm") final NumberForm seatForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        ControllerUtils.filterStatusParser(filterStatus).orElseThrow(() -> new LongParseException(filterStatus));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/reservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, filterStatus, Integer.parseInt(page));


        filterForm.setFilterStatus(filterStatus);
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        mav.addObject("ReservationStatus", ReservationStatus.values());

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("filterStatus", filterStatus);
        mav.addObject("page", Integer.parseInt(page));


        res.checkReservationTime();

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations", method = RequestMethod.POST)
    public ModelAndView reservationsOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                            @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&filterStatus=" + form.getFilterStatus() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/seatCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView seatCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationId") final String reservationIdP,
                                     @RequestParam(value = "page", defaultValue = "1") final String page,
                                     @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                     @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                     @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                     @ModelAttribute("filterForm") final FilterForm filterForm,
                                     @Valid @ModelAttribute("seatForm") final NumberForm seatForm,
                                     final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return reservationsOrderBy(restaurantIdP, orderBy, direction, filterStatus, page, filterForm, seatForm);
        }

        ControllerUtils.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.SEATED);
        res.setTableNumber(reservation, Integer.parseInt(seatForm.getNumber()));

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/showReceipt={reservationId}")
    public ModelAndView showReceipt(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationId") final String reservationIdP,
                                    @RequestParam(value = "page", defaultValue = "1") final String page,
                                    @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                    @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                    @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus) throws Exception {
        ControllerUtils.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("restaurantViews/order/receipt");

        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        mav.addObject("customer", customer);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("filterStatus", filterStatus);
        mav.addObject("page", page);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/finishCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView finishCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationId") final String reservationIdP,
                                       @RequestParam(value = "page", defaultValue = "1") final String page,
                                       @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                       @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                       @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus) throws Exception {
        ControllerUtils.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);

        res.updateReservationStatus(reservation, ReservationStatus.FINISHED);
        cs.addPointsToCustomer(reservation.getCustomer(), res.getTotal(orderItems));

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orderCheckCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView orderCheckCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationId") final String reservationIdP,
                                           @RequestParam(value = "page", defaultValue = "1") final String page,
                                           @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                           @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                           @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus
                                           ) throws Exception {
        ControllerUtils.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long reservationId = Long.parseLong(reservationIdP);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.CHECK_ORDERED);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }




}
