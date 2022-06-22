package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.EditPhoneForm;
import ar.edu.itba.paw.webapp.form.EditEmailForm;
import ar.edu.itba.paw.webapp.form.EditNameForm;
import ar.edu.itba.paw.webapp.form.EditTablesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class RestController {
    private final RestaurantService rs;

    @Autowired
    public RestController(RestaurantService rs) {
        this.rs = rs;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @RequestMapping("/restaurant={restaurantId}/profile")
    public ModelAndView restaurantProfile(@PathVariable("restaurantId") final String restaurantIdP,
                                          final Principal principal) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        Restaurant restaurant = rs.getRestaurantById(Long.parseLong(restaurantIdP)).orElseThrow(RestaurantNotFoundException::new);

        ModelAndView mav = new ModelAndView("restaurantViews/restaurantProfile");
        mav.addObject("restaurant", restaurant);
        mav.addObject("username", principal.getName());
        return mav;
    }


    @RequestMapping("/restaurant={restaurantId}/menu")
    public ModelAndView menuRestaurant(@PathVariable("restaurantId") final String restaurantIdP,
                                       @RequestParam(name = "category", defaultValue = "1") final String categoryIdP) throws Exception {

        ControllerUtils.longParser(restaurantIdP, categoryIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        long categoryId = Long.parseLong(categoryIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/menu/restaurantMenu");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryById(categoryId).orElseThrow(DishCategoryNotFoundException::new);

        mav.addObject("dishes", restaurant.getDishesByCategory(dishCategory));
        mav.addObject("restaurant", restaurant);
        mav.addObject("categories", restaurant.getDishCategories());
        mav.addObject("currentCategory", dishCategory);
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.GET)
    public ModelAndView editTablesForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editTablesForm") final EditTablesForm form) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/editAvailableTables");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setTableQty(String.format("%d", restaurant.getTotalChairs()));
        form.setOpenHour(String.format("%d", restaurant.getOpenHour()));
        form.setCloseHour(String.format("%d", restaurant.getCloseHour()));
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.POST)
    public ModelAndView editMenu(@Valid @ModelAttribute("editTablesForm") final EditTablesForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editTablesForm(restaurantIdP, form);
        }

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        rs.updateRestaurantHourAndTables(restaurant,  Integer.parseInt(form.getTableQty()),
                                                        Integer.parseInt(form.getOpenHour()),
                                                        Integer.parseInt(form.getCloseHour()));

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editName", method = RequestMethod.GET)
    public ModelAndView editNameForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editNameForm") final EditNameForm form) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/editRestaurant/editRestaurantName");
        mav.addObject("restaurant", restaurant);
        form.setName(restaurant.getRestaurantName());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editName", method = RequestMethod.POST)
    public ModelAndView editName(@Valid @ModelAttribute("editNameForm") final EditNameForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editNameForm(restaurantIdP, form);
        }

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        rs.updateRestaurantName(restaurant, form.getName());

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editPhone", method = RequestMethod.GET)
    public ModelAndView editPhoneForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editPhoneForm") final EditPhoneForm form) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/editRestaurant/editRestaurantPhone");
        mav.addObject("restaurant", restaurant);
        form.setPhone(restaurant.getPhone());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editPhone", method = RequestMethod.POST)
    public ModelAndView editPhone(@Valid @ModelAttribute("editPhoneForm") final EditPhoneForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editPhoneForm(restaurantIdP, form);
        }

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        rs.updatePhone(restaurant, form.getPhone());

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editMail", method = RequestMethod.GET)
    public ModelAndView editEmailForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editEmailForm") final EditEmailForm form) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/editRestaurant/editRestaurantEmail");
        mav.addObject("restaurant", restaurant);
        form.setMail(restaurant.getMail());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editMail", method = RequestMethod.POST)
    public ModelAndView editEmail(@Valid @ModelAttribute("editEmailForm") final EditEmailForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editEmailForm(restaurantIdP, form);
        }
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        rs.updateRestaurantEmail(restaurant, form.getMail());
        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }
}
