package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils;
import ar.edu.itba.paw.webapp.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.LongParseException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.CategoryForm;
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

    private final RestaurantService rs;
    private final DishService ds;
    private final ImageService ims;

    @Autowired
    public DishController(final DishService ds, final ImageService ims, final RestaurantService rs) {
        this.ds = ds;
        this.ims = ims;
        this.rs = rs;
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
                                       @RequestParam (name="currentCategory", defaultValue = "2") final String categoryIdP,
                                       @ModelAttribute("createDishForm") final EditDishForm form) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        ModelAndView mav = new ModelAndView("restaurantViews/dish/createDish");
        long restaurantId = Long.parseLong(restaurantIdP);
        long categoryId = Long.parseLong(categoryIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        mav.addObject("categories", restaurant.getDishCategoriesAsMap());
        mav.addObject("category", restaurant.getDishCategoryOfId(categoryId));

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/category/create", method = RequestMethod.GET)
    public ModelAndView createCategoryForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                           @ModelAttribute("createCategoryForm") final CategoryForm createCategoryForm) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/createCategory");

        mav.addObject("restaurant", restaurant);

        return mav;
    }
    @RequestMapping(value = "/restaurant={restaurantId}/category/create", method = RequestMethod.POST)
    public ModelAndView createCategory(@PathVariable ("restaurantId") final String restaurantIdP,
                                       @Valid @ModelAttribute("createCategoryForm") final CategoryForm createCategoryForm, final BindingResult errors) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        if (errors.hasErrors()){
            return createCategoryForm(restaurantIdP, createCategoryForm);
        }

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        rs.createDishCategory(restaurant, createCategoryForm.getCategoryName());

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/category={category}/edit", method = RequestMethod.GET)
    public ModelAndView editCategory(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @PathVariable ("category") final String category,
                                           @ModelAttribute("createCategoryForm") final CategoryForm form) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryByName(category).orElseThrow(DishCategoryNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/createCategory");
        form.setCategoryName(dishCategory.getName());
        mav.addObject("restaurant", restaurant);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/category={category}/edit", method = RequestMethod.POST)
    public ModelAndView editCategory(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @PathVariable ("category") final String category,
                                     @Valid @ModelAttribute("createCategoryForm") final CategoryForm createCategoryForm, final BindingResult errors) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);

        if (errors.hasErrors()){
            return createCategoryForm(restaurantIdP, createCategoryForm);
        }

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryByName(category).orElseThrow(DishCategoryNotFoundException::new);

        rs.editDishCategory(dishCategory, createCategoryForm.getCategoryName());

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");
    }





    @RequestMapping(value = "/restaurant={restaurantId}/category/delete", method = RequestMethod.GET)
    public ModelAndView deleteCategory(@PathVariable ("restaurantId") final String restaurantIdP,
                                       @RequestParam (name="categoryId") final String categoryIdP) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        long categoryId = Long.parseLong(categoryIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/deleteCategoryConfirmation");

        mav.addObject("restaurant", restaurant);
        mav.addObject("categoryName", restaurant.getDishCategoryOfId(categoryId));
        mav.addObject("categoryId", categoryId);
        mav.addObject("canBeDeleted", restaurant.canCategoryBeDeleted(categoryId));

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/category/delete", method = RequestMethod.POST)
    public ModelAndView deleteCategory_post(@PathVariable ("restaurantId") final String restaurantIdP,
                                       @RequestParam (name="categoryId") final String categoryIdP) throws Exception {

        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        long categoryId = Long.parseLong(categoryIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        rs.deleteCategory(restaurant, categoryId);
        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.POST)
    public ModelAndView createDish(@PathVariable ("restaurantId") final String restaurantIdP,
                                   @RequestParam ( name = "currentCategory", defaultValue = "2") final String categoryIdP,
                                   @Valid @ModelAttribute("createDishForm") final EditDishForm createDishForm, final BindingResult errors) throws Exception {
        ControllerUtils.longParser(restaurantIdP).orElseThrow(() -> new LongParseException(restaurantIdP));
        long restaurantId = Long.parseLong(restaurantIdP);
        long categoryId = Long.parseLong(categoryIdP);

        if (errors.hasErrors()){
            return createDishForm(restaurantIdP, categoryIdP,createDishForm);
        }

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        DishCategory dishCategory = rs.getDishCategoryByName(createDishForm.getCategory()).orElseThrow(DishNotFoundException::new);
        // Dish create(long restaurantId, String dishName, String dishDescription, double price);

        Dish dish = rs.createDish(restaurant, createDishForm.getDishName(), createDishForm.getDishDesc(), Double.parseDouble(createDishForm.getDishPrice()), 0, dishCategory);
        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/confirmDish=" + dish.getId());
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/dish={dishId}/edit-photo", method = RequestMethod.POST)
    public ModelAndView editPhoto(@PathVariable ("restaurantId") final String restaurantIdP,
                                  @PathVariable ("dishId") final String dishIdP,
                                  @RequestParam CommonsMultipartFile photo) throws Exception {

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long dishId = Long.parseLong(dishIdP);
        // Dish create(long restaurantId, String dishName, String dishDescription, double price);
        long imageId = ims.createImage(photo);
        ds.updateDishPhoto(dishId, imageId);

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
        long restaurantId = Long.parseLong(restaurantIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        rs.deleteDish(restaurant, dishId);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");

    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.GET)
    public ModelAndView editDishForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                     @ModelAttribute("editDishForm") final EditDishForm form,
                                     @PathVariable("dishId") final String dishIdP) throws Exception {

        ControllerUtils.longParser(dishIdP, restaurantIdP).orElseThrow(() -> new LongParseException(""));
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        final ModelAndView mav = new ModelAndView("restaurantViews/dish/editDish");
        mav.addObject("restaurantId", restaurantId);
        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);
        mav.addObject("categories", restaurant.getDishCategoriesAsMap());

        form.setDishName(dish.getDishName());
        form.setDishDesc(dish.getDishDescription());
        form.setDishPrice("" + dish.getPrice());
        form.setCategory(dish.getCategory().getName());
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
        DishCategory dishCategory = rs.getDishCategoryByName(form.getCategory()).orElseThrow(DishNotFoundException::new);

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        ds.updateDish(dish, form.getDishName(), form.getDishDesc(), Double.parseDouble(form.getDishPrice()),  dishCategory);

        return new ModelAndView("redirect:/restaurant=" + restaurantIdP + "/menu");
    }
}
