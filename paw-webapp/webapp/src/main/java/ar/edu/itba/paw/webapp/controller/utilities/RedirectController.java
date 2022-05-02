package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.FindReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

@Controller
public class RedirectController {

    private RestaurantService rs;
    private CustomerService cs;
    private ReservationService res;

    @Autowired
    public RedirectController(final RestaurantService rs, CustomerService cs, ReservationService res) {
        this.rs = rs;
        this.cs = cs;
        this.res = res;
    }

    @RequestMapping(value = "/redirect")
    public ModelAndView redirectLogin(Authentication authentication, Principal principal) {

        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (Objects.equals(role, "ROLE_RESTAURANT")) {
            Restaurant restaurant = rs.getRestaurantByUsername(principal.getName()).orElseThrow(RestaurantNotFoundException::new);
            return new ModelAndView("redirect:/restaurant=" + restaurant.getId() + "/menu");
        }
        return new ModelAndView("redirect:/history");
    }
    @RequestMapping(value = "/createReservation-3/{reservationId}/redirect")
    public ModelAndView redirectCreateReservation(@PathVariable("reservationId") final String reservationIdP,
                                                  Authentication authentication, Principal principal) {
        if (authentication != null) {
            if (!authentication.getAuthorities().isEmpty()) {
                String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();

                if (Objects.equals(role, "ROLE_CUSTOMER")) {
                    Customer customer = cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
                    return new ModelAndView("redirect:/confirmReservation/" + reservationIdP);
                }
            }
        }
        return new ModelAndView("redirect:/createReservation-3/" + reservationIdP);
    }
}
