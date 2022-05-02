package ar.edu.itba.paw.webapp.controller.utilities;

import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.ControllerService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.FindReservationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

@Controller
public class RedirectController {

    private RestaurantService rs;

    @Autowired
    public RedirectController(final RestaurantService rs) {
        this.rs = rs;
    }

    @RequestMapping(value = "/redirect")
    public ModelAndView findReservationForm(Authentication authentication, Principal principal) {

        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (Objects.equals(role, "ROLE_RESTAURANT")) {
            Restaurant restaurant = rs.getRestaurantByUsername(principal.getName()).orElseThrow(RestaurantNotFoundException::new);
            return new ModelAndView("redirect:/restaurant=" + restaurant.getId() + "/menu");
        }
        return new ModelAndView("redirect:/history");
    }
}
