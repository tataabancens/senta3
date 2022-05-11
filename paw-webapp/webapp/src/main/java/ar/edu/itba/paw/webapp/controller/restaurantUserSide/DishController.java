package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.enums.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.form.EditDishForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class DishController {

    private final DishService ds;
    private final ImageService ims;

    @Autowired
    public DishController(final DishService ds, final ImageService ims) {
        this.ds = ds;
        this.ims = ims;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/confirmDish={dishId}", method = RequestMethod.GET)
    public ModelAndView confirmDish(@PathVariable("restaurantId") final String restaurantIdP,
                                    @PathVariable("dishId") final String dishIdP) throws Exception {

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
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
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ModelAndView mav = new ModelAndView("restaurantViews/dish/createDish");
        mav.addObject("categories", DishCategory.getAsMap());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.POST)
    public ModelAndView createDish(@PathVariable ("restaurantId") final String restaurantIdP,
                                   @Valid @ModelAttribute("createDishForm") final EditDishForm createDishForm, final BindingResult errors) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        if (errors.hasErrors()){
            return createDishForm(restaurantIdP, createDishForm);
        }

        // Dish create(long restaurantId, String dishName, String dishDescription, double price);

        Dish dish = ds.create(restaurantId, createDishForm.getDishName(), createDishForm.getDishDesc(), Double.parseDouble(createDishForm.getDishPrice()), 0, createDishForm.getCategory());

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/confirmDish=" + dish.getId());
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/dish={dishId}/edit-photo", method = RequestMethod.POST)
    public ModelAndView editPhoto(@PathVariable ("restaurantId") final String restaurantIdP,
                                  @PathVariable ("dishId") final String dishIdP,
                                  @RequestParam CommonsMultipartFile photo) throws Exception {

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long dishId = Long.parseLong(dishIdP);
        // Dish create(long restaurantId, String dishName, String dishDescription, double price);
        Image image = ims.createImage(photo);
        ds.updateDishPhoto(dishId, image.getImageId());

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/confirmDish=" + dishId);
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/deleteDish={dishId}", method = RequestMethod.GET)
    public ModelAndView deleteDishConfirmation(@PathVariable("dishId") final String dishIdP,
                                               @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/deleteDish");

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/deleteDish={dishId}", method = RequestMethod.POST)
    public ModelAndView deleteDishConfirmationPost(@PathVariable("dishId") final String dishIdP,
                                               @PathVariable("restaurantId") final String restaurantIdP) throws Exception {
        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long dishId = Long.parseLong(dishIdP);

        ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        ds.deleteDish(dishId);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");

    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.GET)
    public ModelAndView editDishForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editDishForm") final EditDishForm form,
                                     @PathVariable("dishId") final String dishIdP) throws Exception {

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/editDish");
        mav.addObject("restaurantId", restaurantId);
        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("categories", DishCategory.getAsMap());

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

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long dishId = Long.parseLong(dishIdP);

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        ds.updateDish(dishId, form.getDishName(), form.getDishDesc(), Double.parseDouble(form.getDishPrice()),  form.getCategory(), dish.getRestaurantId());

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");
    }
}
