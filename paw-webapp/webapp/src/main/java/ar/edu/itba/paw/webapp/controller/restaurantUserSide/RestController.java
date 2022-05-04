package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.EditREstaurantPhoneForm;
import ar.edu.itba.paw.webapp.form.EditRestaurantEmailForm;
import ar.edu.itba.paw.webapp.form.EditRestaurantNameForm;
import ar.edu.itba.paw.webapp.form.EditTablesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class RestController {
    RestaurantService rs;
    ReservationService res;
    DishService ds;
    ImageService ims;
    ControllerService controllerService;

    @Autowired
    public RestController(RestaurantService rs, ReservationService res,
                          DishService ds, ImageService ims, ControllerService controllerService) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
        this.ims = ims;
        this.controllerService = controllerService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId){
        final ModelAndView mav = new ModelAndView("login");

        return mav;
    }


    @RequestMapping("/restaurant={restaurantId}")
    public ModelAndView restaurant(@RequestParam(name = "userId", defaultValue = "1") final long userId,
                                   @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        return new ModelAndView("aa_Trash/restaurantTest");
    }
    @RequestMapping("/restaurant={restaurantId}/profile")
    public ModelAndView restaurantProfile(@RequestParam(name = "userId", defaultValue = "1") final long userId,
                                          @PathVariable("restaurantId") final String restaurantIdP,
                                          Principal principal) throws Exception {

        controllerService.longParser(restaurantIdP);
        Restaurant restaurant=rs.getRestaurantById(Long.parseLong(restaurantIdP)).orElseThrow(RestaurantNotFoundException::new);

        ModelAndView mav=new ModelAndView("profile");
        mav.addObject("restaurant", restaurant);
        mav.addObject("username", principal.getName());
        return mav;
    }


    @RequestMapping("/restaurant={restaurantId}/menu")
    public ModelAndView menuRestaurant(@PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("menu/RestaurantMenu");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(restaurantId));
        mav.addObject("restaurant", restaurant);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.GET)
    public ModelAndView editTablesForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editTablesForm") final EditTablesForm form) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("/editAvailableTables");
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

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        rs.updateRestaurantHourAndTables(restaurantId,  Integer.parseInt(form.getTableQty()),
                                                        Integer.parseInt(form.getOpenHour()),
                                                        Integer.parseInt(form.getCloseHour()));

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editName", method = RequestMethod.GET)
    public ModelAndView editNameForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editNameForm") final EditRestaurantNameForm form) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("editRestaurant/editRestaurantName");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setName(restaurant.getRestaurantName());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editName", method = RequestMethod.POST)
    public ModelAndView editName(@Valid @ModelAttribute("editNameForm") final EditRestaurantNameForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editNameForm(restaurantIdP, form);
        }

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        rs.updateRestaurantName(form.getName(), restaurantId);

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editPhone", method = RequestMethod.GET)
    public ModelAndView editPhoneForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editPhoneForm") final EditREstaurantPhoneForm form) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("editRestaurant/editRestaurantPhone");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setPhone(restaurant.getPhone());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editPhone", method = RequestMethod.POST)
    public ModelAndView editPhone(@Valid @ModelAttribute("editPhoneForm") final EditREstaurantPhoneForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editPhoneForm(restaurantIdP, form);
        }

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        rs.updatePhone(form.getPhone(), restaurantId);

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editMail", method = RequestMethod.GET)
    public ModelAndView editEmailForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editEmailForm") final EditRestaurantEmailForm form) throws Exception {

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("editRestaurant/editRestaurantEmail");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setMail(restaurant.getMail());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editMail", method = RequestMethod.POST)
    public ModelAndView editEmail(@Valid @ModelAttribute("editEmailForm") final EditRestaurantEmailForm form,
                                 final BindingResult errors,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        if (errors.hasErrors()) {
            return editEmailForm(restaurantIdP, form);
        }

        controllerService.longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        rs.updateRestaurantEmail(form.getMail(), restaurantId);

        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/profile");
    }

}
