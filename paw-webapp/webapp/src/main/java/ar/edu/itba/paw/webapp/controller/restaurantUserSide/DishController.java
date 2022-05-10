package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.DeleteDishForm;
import ar.edu.itba.paw.webapp.form.EditDishForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class DishController {

    RestaurantService rs;
    ReservationService res;
    DishService ds;
    ImageService ims;
    private ControllerService controllerService;

    @Autowired
    public DishController(RestaurantService rs, ReservationService res,
                                DishService ds, ImageService ims, ControllerService controllerService) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
        this.ims = ims;
        this.controllerService = controllerService;

    }

    @RequestMapping(value = "/restaurant={restaurantId}/confirmDish={dishId}", method = RequestMethod.GET)
    public ModelAndView confirmDish(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                                    @PathVariable("restaurantId") final String restaurantIdP,
                                    @PathVariable("dishId") final String dishIdP) throws Exception {

        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/dishConfirmation");
        mav.addObject("dishId", dishId);
        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("imageId", dish.getImageId());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.GET)
    public ModelAndView createDishForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                       @ModelAttribute("createDishForm") final EditDishForm form) throws Exception {
        controllerService.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        ModelAndView mav = new ModelAndView("restaurantViews/dish/createDish");
        mav.addObject("categories", DishCategory.getAsList());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.POST)
    public ModelAndView createDish(@PathVariable ("restaurantId") final String restaurantIdP,
                                   @Valid @ModelAttribute("createDishForm") final EditDishForm createDishForm, final BindingResult errors) throws Exception {
        controllerService.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        if (errors.hasErrors()){
            return createDishForm(restaurantIdP, createDishForm);
        }

        // Dish create(long restaurantId, String dishName, String dishDescription, double price);

        Dish dish = ds.create(restaurantId, createDishForm.getDishName(), createDishForm.getDishDesc(), Double.parseDouble(createDishForm.getDishPrice()), 0, createDishForm.getCategory());

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/confirmDish=" + dish.getId());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/dish={dishId}/edit-photo", method = RequestMethod.POST)
    public ModelAndView editPhoto(@PathVariable ("restaurantId") final String restaurantIdP,
                                  @PathVariable ("dishId") final String dishIdP,
                                  @RequestParam CommonsMultipartFile photo) throws Exception {

        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);
        // Dish create(long restaurantId, String dishName, String dishDescription, double price);
        Image image = ims.createImage(photo);
        ds.updateDishPhoto(dishId, image.getImageId());
        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/confirmDish=" + dishId);
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/deleteDish={dishId}", method = RequestMethod.GET)
    public ModelAndView deleteDishConfirmation(@PathVariable("dishId") final String dishIdP,
                                               @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/deleteDish");

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/deleteDish={dishId}", method = RequestMethod.POST)
    public ModelAndView deleteDishConfirmationPost(@PathVariable("dishId") final String dishIdP,
                                               @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        ds.deleteDish(dishId);

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.GET)
    public ModelAndView editDishForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editDishForm") final EditDishForm form,
                                     @PathVariable("dishId") final String dishIdP) throws Exception {

        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/editDish");
        mav.addObject("restaurantId", restaurantId);
        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("categories", DishCategory.getAsList());

        form.setDishName(dish.getDishName());
        form.setDishDesc(dish.getDishDescription());
        form.setDishPrice("" + dish.getPrice());
        form.setCategory(dish.getCategory());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.POST)
    public ModelAndView editDishForm(@Valid @ModelAttribute("editDishForm") final EditDishForm form,
                                     final BindingResult errors,
                                 @PathVariable("dishId") final String dishIdP,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        if (errors.hasErrors()) {
            return editDishForm(restaurantIdP, form, dishIdP);
        }

        controllerService.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(restaurantId));
        mav.addObject("restaurant", restaurant);

        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        ds.updateDish(dishId, form.getDishName(), form.getDishDesc(), Double.parseDouble(form.getDishPrice()),  form.getCategory(), dish.getRestaurantId());

        return mav;
    }
}
