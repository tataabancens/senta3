package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RedirectController {

    private final RestaurantService rs;
    private final CustomerService cs;

    @Autowired
    public RedirectController(final RestaurantService rs, CustomerService cs) {
        this.rs = rs;
        this.cs = cs;
    }

    @RequestMapping(value = "/redirect")
    public ModelAndView redirectLogin(Authentication authentication, Principal principal) {
        Optional<? extends GrantedAuthority> maybeAuthority = authentication.getAuthorities().stream().findFirst();
        if(maybeAuthority.isPresent()) {
            String role = maybeAuthority.get().getAuthority();
            if (Objects.equals(role, "ROLE_RESTAURANT")) {
                Restaurant restaurant = rs.getRestaurantByUsername(principal.getName()).orElseThrow(RestaurantNotFoundException::new);
                return new ModelAndView("redirect:/restaurant=" + restaurant.getId() + "/menu");
            }
            if (Objects.equals(role, "ROLE_WAITER")) {
                return new ModelAndView("redirect:/restaurant=1/waiter");
            }
            if (Objects.equals(role, "ROLE_KITCHEN")) {
                return new ModelAndView("redirect:/restaurant=1/orders");
            }
        }
        return new ModelAndView("redirect:/active-reservations");
    }
    @RequestMapping(value = "/createReservation-3/{reservationSecurityCode}/redirect")
    public ModelAndView redirectCreateReservation(@PathVariable("reservationSecurityCode") final String reservationSecurityCode,
                                                  Authentication authentication, Principal principal) {

        if (authentication != null) {
            if (!authentication.getAuthorities().isEmpty()) {
                String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();

                if (Objects.equals(role, "ROLE_CUSTOMER")) {
                    cs.getCustomerByUsername(principal.getName()).orElseThrow(CustomerNotFoundException::new);
                    return new ModelAndView("redirect:/confirmReservation/" + reservationSecurityCode);
                }
            }
        }
        return new ModelAndView("redirect:/createReservation-4/" + reservationSecurityCode);
    }

    @RequestMapping(value = "/profile")
    public ModelAndView redirectProfile(Authentication authentication, Principal principal) {

        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (Objects.equals(role, "ROLE_RESTAURANT")) {
            Restaurant restaurant = rs.getRestaurantByUsername(principal.getName()).orElseThrow(RestaurantNotFoundException::new);
            return new ModelAndView("redirect:/restaurant=" + restaurant.getId() + "/profile");
        } else if (Objects.equals(role, "ROLE_CUSTOMER")) {
            return new ModelAndView("redirect:/customerProfile");
        }
        return new ModelAndView("redirect:/");
    }
}
