package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.DishService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.EditDishForm;
import ar.edu.itba.paw.webapp.form.EditTablesForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import javax.validation.Valid;

@Controller
public class RestaurantController {
    RestaurantService rs;
    ReservationService res;
    DishService ds;
    ImageService ims;

    @Autowired
    public RestaurantController(RestaurantService rs, ReservationService res,
                                DishService ds, ImageService ims) {
        this.rs = rs;
        this.res = res;
        this.ds = ds;
        this.ims = ims;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId){
        final ModelAndView mav = new ModelAndView("login");

        return mav;
    }


    @RequestMapping("/restaurant={restaurantId}")
    public ModelAndView restaurant(@RequestParam(name = "userId", defaultValue = "1") final long userId,
                                   @PathVariable("restaurantId") final int restaurantId) {

        return new ModelAndView("restaurantTest");
    }


    @RequestMapping("/restaurant={restaurantId}/menu")
    public ModelAndView menuRestaurant(@PathVariable("restaurantId") final int restaurantId) {

        final ModelAndView mav = new ModelAndView("RestaurantMenu");
        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getReservationsByStatus(ReservationStatus.ACTIVE);
        for (Reservation reservation : reservations) {
            res.updateOrderItemsStatus(reservation.getReservationId(), OrderItemStatus.ORDERED, OrderItemStatus.INCOMING);
        }
        mav.addObject("reservations", reservations);


        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.GET)
    public ModelAndView editForm(@PathVariable ("restaurantId") final long restaurantId, @ModelAttribute("editDishForm") final EditDishForm form, @PathVariable("dishId") final int dishId){

        final ModelAndView mav = new ModelAndView("/editDish");
        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);
        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        form.setDishName(dish.getDishName());
        form.setDishDesc(dish.getDishDescription());
        form.setDishPrice((double) dish.getPrice());
        return mav;

    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.POST)
    public ModelAndView editMenu(@ModelAttribute("editDishForm") final EditDishForm form,
                                 @PathVariable("dishId") final int dishId,
                                 @PathVariable("restaurantId") final int restaurantId) {

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        Restaurant restaurant=rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(1));
        mav.addObject("restaurant", restaurant);

        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        ds.updateDish(dishId, form.getDishName(), form.getDishDesc(), form.getDishPrice(), dish.getRestaurantId());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                             @PathVariable("restaurantId") final int restaurantId){
        final ModelAndView mav = new ModelAndView("orders");
        List<Reservation> reservations = res.getReservationsByStatus(ReservationStatus.ACTIVE);
        List<FullOrderItem> incomingItems = res.getOrderItemsByStatus(OrderItemStatus.INCOMING);
        List<FullOrderItem> finishedItems = res.getOrderItemsByStatus(OrderItemStatus.FINISHED);
        for (Reservation reservation : reservations) {
            res.updateOrderItemsStatus(reservation.getReservationId(), OrderItemStatus.ORDERED, OrderItemStatus.INCOMING);
        }
        mav.addObject("reservations", reservations);
        mav.addObject("incomingItems", incomingItems);
        mav.addObject("finishedItems", finishedItems);
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/incomingToFinished-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusFinished (@PathVariable("restaurantId") final int restaurantId,
                                                 @PathVariable("orderItemId") final int orderItemId){

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.FINISHED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/finishedToDelivered-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusDelivered (@PathVariable("restaurantId") final int restaurantId,
                                                 @PathVariable("orderItemId") final int orderItemId) {

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.DELIVERED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/confirmDish={dishId}", method = RequestMethod.GET)
    public ModelAndView confirmDish(@RequestParam(name = "reservationId", defaultValue = "1") final long reservationId,
                             @PathVariable("restaurantId") final int restaurantId,
                                    @PathVariable("dishId") final int dishId){

        final ModelAndView mav = new ModelAndView("dishConfirmation");
        mav.addObject("dishId", dishId);
        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("imageId", dish.getImageId());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.GET)
    public ModelAndView createDishForm(@PathVariable ("restaurantId") final long restaurantId, @ModelAttribute("createDishForm") final EditDishForm form){
        return new ModelAndView("createDish");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/create", method = RequestMethod.POST)
    public ModelAndView createDish(@PathVariable ("restaurantId") final long restaurantId,
                                   @Valid @ModelAttribute("createDishForm") final EditDishForm createDishForm, final BindingResult errors) {
        if (errors.hasErrors()){
            return createDishForm(restaurantId, createDishForm);
        }

        // Dish create(long restaurantId, String dishName, String dishDescription, double price);

        Dish dish = ds.create(restaurantId, createDishForm.getDishName(), createDishForm.getDishDesc(), createDishForm.getDishPrice(), 1);

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/confirmDish=" + dish.getId());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/dish={dishId}/edit-photo", method = RequestMethod.POST)
    public ModelAndView editPhoto(@PathVariable ("restaurantId") final long restaurantId,
                                  @PathVariable ("dishId") final long dishId,
                                  @RequestParam CommonsMultipartFile photo) {

        // Dish create(long restaurantId, String dishName, String dishDescription, double price);
        Image image = ims.createImage(photo);
        ds.updateDishPhoto(dishId, image.getImageId());
        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/confirmDish=" + dishId);
        return mav;
    }


    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.GET)
    public ModelAndView editForm(@PathVariable ("restaurantId") final long restaurantId, @ModelAttribute("editTablesForm") final EditTablesForm form){

        final ModelAndView mav = new ModelAndView("/editAvailableTables");
        Restaurant restaurant = rs.getRestaurantById(1).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setTableQty(restaurant.getTotalTables());
        form.setOpenHour(restaurant.getOpenHour());
        form.setCloseHour(restaurant.getCloseHour());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.POST)
    public ModelAndView editMenu(@ModelAttribute("editTablesForm") final EditTablesForm form,
                                 @PathVariable("restaurantId") final int restaurantId) {

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        rs.updateRestaurantHourAndTables(restaurantId, form.getTableQty(), form.getOpenHour(), form.getCloseHour());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.GET)
    public ModelAndView cancelReservationConfirmation(@PathVariable("reservationId") final int reservationId,
                                    @PathVariable("restaurantId") final int restaurantId){
        final ModelAndView mav = new ModelAndView("cancelReservationConfirmation");
        mav.addObject("reservationId", reservationId);
        mav.addObject("restaurantId", restaurantId);

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/cancelReservationConfirmation/id={reservationId}", method = RequestMethod.POST)
    public ModelAndView cancelReservationConfirmationPost(@PathVariable("reservationId") final int reservationId,
                                                      @PathVariable("restaurantId") final int restaurantId){
        res.cancelReservation(restaurantId, reservationId);
        return new ModelAndView("redirect:/restaurant=" + restaurantId + "/menu");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/deleteDish={dishId}", method = RequestMethod.GET)
    public ModelAndView deleteDishConfirmation(@PathVariable("dishId") final int dishId,
                                                      @PathVariable("restaurantId") final int restaurantId){
        final ModelAndView mav = new ModelAndView("deleteDish");

        Dish dish = ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        ds.deleteDish(dishId);

        mav.addObject("dish", dish);

        return mav;
    }

}
