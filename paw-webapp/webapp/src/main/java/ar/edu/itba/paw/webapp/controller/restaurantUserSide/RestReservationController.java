package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.enums.ReservationStatus;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import ar.edu.itba.paw.webapp.form.FilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RestReservationController {

    private CustomerService cs;
    private RestaurantService rs;
    private ReservationService res;
    private MailingService ms;
    private ControllerService controllerService;

    @Autowired
    public RestReservationController(CustomerService cs, RestaurantService rs, ReservationService res, MailingService ms, ControllerService controllerService) {
        this.cs = cs;
        this.rs = rs;
        this.res = res;
        this.ms = ms;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.GET)
    public ModelAndView cancelReservationConfirmation(@PathVariable("reservationId") final String reservationIdP,
                                                      @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/cancelReservationConfirmation");
        mav.addObject("reservationId", reservationId);
        mav.addObject("restaurantId", restaurantId);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirmationPost(@PathVariable("reservationId") final String reservationIdP,
                                                          @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(reservationIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        Restaurant restaurant = rs.getRestaurantById(reservation.getRestaurantId()).orElseThrow(RestaurantNotFoundException::new);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        res.updateReservationStatus(reservationId, ReservationStatus.CANCELED);

        ms.sendCancellationEmail(restaurant,customer,reservation);

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/reservations");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/reservations") //?orderBy=String
    public ModelAndView reservationsOrderBy(@PathVariable("restaurantId") final String restaurantIdP,
                                            @RequestParam(value = "orderBy", defaultValue = "reservationid") final String orderBy,
                                            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
                                            @RequestParam(value = "filterStatus", defaultValue = "0") final String filterStatus,
                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                            @ModelAttribute("filterForm") final FilterForm filterForm) throws Exception {


        controllerService.orderByParser(orderBy).orElseThrow(() -> new OrderByException(orderBy));
        controllerService.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        controllerService.directionParser(direction).orElseThrow(() -> new OrderByException(orderBy));
        controllerService.longParser(page).orElseThrow(() -> new LongParseException(page));
        controllerService.longParser(filterStatus).orElseThrow(() -> new LongParseException(filterStatus));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/reservation/reservations");
        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("restaurant", restaurant);

        List<FullReservation> reservations = res.getAllReservationsOrderedBy(restaurantId, orderBy, direction, filterStatus, Integer.parseInt(page));


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

    @RequestMapping(value = "/restaurant={restaurantId}/reservations", method = RequestMethod.POST) //?orderBy=String
    public ModelAndView reservationsOrderByPost(@PathVariable("restaurantId") final String restaurantIdP,
                                            @RequestParam(value = "page", defaultValue = "1") final String page,
                                            @ModelAttribute("filterForm") final FilterForm form) throws Exception {


        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/reservations?orderBy=" + form.getOrderBy() +
                "&direction=" + form.getDirection() + "&filterStatus=" + form.getFilterStatus() + "&page=" + page);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/seatCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView seatCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.updateReservationStatus(reservationId, ReservationStatus.SEATED);

        return new ModelAndView("redirect:/restaurant="+ restaurantId +"/reservations");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/showReceipt={reservationId}")
    public ModelAndView showReceipt(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        Reservation reservation = res.getReservationById(reservationId).orElseThrow(ReservationNotFoundException::new);
        List<FullOrderItem> orderItems = res.getAllOrderItemsByReservationId(reservationId);
        Customer customer = cs.getUserByID(reservation.getCustomerId()).orElseThrow(CustomerNotFoundException::new);

        ModelAndView mav = new ModelAndView("restaurantViews/order/receipt");

        mav.addObject("discountCoefficient", res.getDiscountCoefficient(reservationId));
        mav.addObject("orderItems", orderItems);
        mav.addObject("restaurant", restaurant);
        mav.addObject("total", res.getTotal(orderItems));
        mav.addObject("reservationId", reservationId);
        mav.addObject("customer", customer);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/finishCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView finishCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                     @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.updateReservationStatus(reservationId, ReservationStatus.FINISHED);

        return new ModelAndView("redirect:/restaurant="+ restaurantId +"/reservations");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orderCheckCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView orderCheckCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                       @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.updateReservationStatus(reservationId, ReservationStatus.CHECK_ORDERED);

        return new ModelAndView("redirect:/restaurant="+ restaurantId +"/reservations");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/removeCustomer={reservationId}", method = RequestMethod.POST)
    public ModelAndView removeCustomer(@PathVariable("restaurantId") final String restaurantIdP,
                                           @PathVariable("reservationId") final String reservationIdP) throws Exception {
        controllerService.longParser(restaurantIdP, reservationIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        res.updateReservationStatus(reservationId, ReservationStatus.REMOVED);

        return new ModelAndView("redirect:/restaurant="+ restaurantId +"/reservations");
    }



}
