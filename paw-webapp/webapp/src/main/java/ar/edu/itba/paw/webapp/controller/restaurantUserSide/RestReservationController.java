package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.FilterForm;
import ar.edu.itba.paw.webapp.form.TableNumberForm;
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

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/securityCode={reservationSecurityCode}", method = RequestMethod.GET)
    public ModelAndView cancelReservationConfirmation(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                      @PathVariable("restaurantId") final String restaurantIdP,
                                                      @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                      @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                      @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                                      @RequestParam(value = "page", defaultValue = "1") final String page) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/cancelReservationConfirmation");
        mav.addObject("reservationSecurityCode", reservationSecurityCode);
        mav.addObject("restaurantId", restaurantId);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("filterStatus", filterStatus);
        mav.addObject("page", Integer.parseInt(page));

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/securityCode={reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirmationPost(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                          @PathVariable("restaurantId") final String restaurantIdP,
                                                          @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                          @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                          @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                                          @RequestParam(value = "page", defaultValue = "1") final String page) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));

        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurant().getId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.CANCELED);

        ms.sendCancellationEmail(restaurant,customer,reservation);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/open?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/open")
    public ModelAndView reservationsOpen(@PathVariable("restaurantId") final String restaurantIdP,
                                            @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                            @ModelAttribute("filterForm") final FilterForm filterForm,
                                            @ModelAttribute("seatForm") final TableNumberForm seatForm) throws Exception {

        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/openReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "0", Integer.parseInt(page));


        filterForm.setFilterStatus("0");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        seatForm.setNumber("0");

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/open", method = RequestMethod.POST)
    public ModelAndView reservationsOpenOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                @RequestParam(value = "page", defaultValue = "1") final String page,
                                                @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/open?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/seated")
    public ModelAndView reservationsSeated(@PathVariable("restaurantId") final String restaurantIdP,
                                         @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                         @RequestParam(value = "page", defaultValue = "1") final String page,
                                         @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/seatedReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "1", Integer.parseInt(page));


        filterForm.setFilterStatus("1");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);


        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/seated", method = RequestMethod.POST)
    public ModelAndView reservationsSeatedOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                @RequestParam(value = "page", defaultValue = "1") final String page,
                                                @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/seated?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/checkordered")
    public ModelAndView reservationsCheckOrdered(@PathVariable("restaurantId") final String restaurantIdP,
                                         @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                         @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                         @RequestParam(value = "page", defaultValue = "1") final String page,
                                         @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/checkOrderedReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "2", Integer.parseInt(page));


        filterForm.setFilterStatus("2");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/checkordered", method = RequestMethod.POST)
    public ModelAndView reservationsCheckOrderedOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                    @RequestParam(value = "page", defaultValue = "1") final String page,
                                                    @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/checkordered?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/finished")
    public ModelAndView reservationsFinished(@PathVariable("restaurantId") final String restaurantIdP,
                                                 @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                 @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                 @RequestParam(value = "page", defaultValue = "1") final String page,
                                                 @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/finishedReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "3", Integer.parseInt(page));


        filterForm.setFilterStatus("3");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/finished", method = RequestMethod.POST)
    public ModelAndView reservationsFinishedOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                                            @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/finished?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/canceled")
    public ModelAndView reservationsCanceled(@PathVariable("restaurantId") final String restaurantIdP,
                                                 @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                                 @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                                 @RequestParam(value = "page", defaultValue = "1") final String page,
                                                 @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/cancelledReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "4", Integer.parseInt(page));


        filterForm.setFilterStatus("4");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/canceled", method = RequestMethod.POST)
    public ModelAndView reservationsCanceledOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                                            @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/canceled?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations/all")
    public ModelAndView reservationsAll(@PathVariable("restaurantId") final String restaurantIdP,
                                              @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                              @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                              @RequestParam(value = "page", defaultValue = "1") final String page,
                                              @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        ControllerUtils.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ControllerUtils.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        ControllerUtils.longParser(page).orElseThrow(() -> new LongParseException(page));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/allReservations");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, "9", Integer.parseInt(page));


        filterForm.setFilterStatus("9");
        filterForm.setDirection(direction);
        filterForm.setOrderBy(orderBy);

        mav.addObject("reservations", reservations);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("page", Integer.parseInt(page));

        res.checkReservationTime();

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/reservations/all", method = RequestMethod.POST)
    public ModelAndView reservationsAllOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                                         @RequestParam(value = "page", defaultValue = "1") final String page,
                                                         @ModelAttribute("filterForm") final FilterForm form){


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/all?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&page=" + page);
    }

    /*
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

     */

    @RequestMapping(value = "/restaurant={restaurantId}/seatCustomer={reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView seatCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                     @RequestParam(value = "page", defaultValue = "1") final String page,
                                     @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                     @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                     @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus,
                                     @ModelAttribute("filterForm") final FilterForm filterForm,
                                     @Valid @ModelAttribute("seatForm") final TableNumberForm seatForm,
                                     final BindingResult errors) throws Exception {
        if (errors.hasErrors()){
            return reservationsOpen(restaurantIdP, orderBy, direction, page, filterForm, seatForm);
        }

        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.SEATED);
        res.setTableNumber(reservation, Integer.parseInt(seatForm.getNumber()));

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/open?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/showReceipt={reservationSecurityCode}")
    public ModelAndView showReceipt(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                    @RequestParam(value = "page", defaultValue = "1") final String page,
                                    @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                    @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                    @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);
        Customer customer = cs.getCustomerById(reservation.getCustomer().getId()).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("restaurantViews/order/receipt");

        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservation.getId()));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationSecurityCode", reservationSecurityCode);
        mav.addObject("customer", customer);
        mav.addObject("orderBy", orderBy);
        mav.addObject("direction", direction);
        mav.addObject("filterStatus", filterStatus);
        mav.addObject("page", page);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/finishCustomer={reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView finishCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                       @RequestParam(value = "page", defaultValue = "1") final String page,
                                       @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                       @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                       @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);
        List<OrderItem> orderItems = res.getAllOrderItemsByReservation(reservation);

        res.updateReservationStatus(reservation, ReservationStatus.FINISHED);
        cs.addPointsToCustomer(reservation.getCustomer(), res.getTotal(orderItems));

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/checkordered?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orderCheckCustomer={reservationSecurityCode}", method = RequestMethod.POST)
    public ModelAndView orderCheckCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                           @RequestParam(value = "page", defaultValue = "1") final String page,
                                           @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                           @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                           @RequestParam(value = "filterStatus", defaultValue = "") final String filterStatus
                                           ) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(""));
        Reservation reservation = res.getReservationBySecurityCode(reservationSecurityCode).orElseThrow(ReservationNotFoundException::new);

        res.updateReservationStatus(reservation, ReservationStatus.CHECK_ORDERED);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations/seated?orderBy=" + orderBy +
                "&direction=" + direction + "&filterStatus=" + filterStatus + "&page=" + page);
    }




}
