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
                                   @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        return new ModelAndView("aa_Trash/restaurantTest");
    }


    @RequestMapping("/restaurant={restaurantId}/menu")
    public ModelAndView menuRestaurant(@PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("menu/RestaurantMenu");
        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(restaurantId));
        mav.addObject("restaurant", restaurant);

        List<Reservation> reservations = res.getReservationsByStatus(ReservationStatus.ACTIVE);
        for (Reservation reservation : reservations) {
            res.updateOrderItemsStatus(reservation.getReservationId(), OrderItemStatus.ORDERED, OrderItemStatus.INCOMING);
        }
        mav.addObject("reservations", reservations);


        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/menu/edit/dishId={dishId}", method = RequestMethod.GET)
    public ModelAndView editForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editDishForm") final EditDishForm form,
                                 @PathVariable("dishId") final String dishIdP) throws Exception {

        longParser(dishIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("dish/editDish");
        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(restaurantId));
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
                                 @PathVariable("dishId") final String dishIdP,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        longParser(dishIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long dishId = Long.parseLong(dishIdP);

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        Restaurant restaurant=rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        restaurant.setDishes(rs.getRestaurantDishes(restaurantId));
        mav.addObject("restaurant", restaurant);

        Dish dish =  ds.getDishById(dishId).orElseThrow(DishNotFoundException::new);
        mav.addObject("dish", dish);

        ds.updateDish(dishId, form.getDishName(), form.getDishDesc(), form.getDishPrice(), dish.getRestaurantId());

        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders", method = RequestMethod.GET)
    public ModelAndView menu(@RequestParam(name = "reservationId", defaultValue = "1") final String reservationIdP,
                             @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        longParser(reservationIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long reservationId = Long.parseLong(reservationIdP);

        final ModelAndView mav = new ModelAndView("order/orders");
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
    public ModelAndView OrderItemStatusFinished (@PathVariable("restaurantId") final String restaurantIdP,
                                                 @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        longParser(orderItemIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.FINISHED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }

    @RequestMapping(value = "/restaurant={restaurantId}/orders/finishedToDelivered-{orderItemId}", method = RequestMethod.POST)
    public ModelAndView OrderItemStatusDelivered (@PathVariable("restaurantId") final String restaurantIdP,
                                                 @PathVariable("orderItemId") final String orderItemIdP) throws Exception {

        longParser(orderItemIdP, restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);
        long orderItemId = Long.parseLong(orderItemIdP);

        res.updateOrderItemStatus(orderItemId, OrderItemStatus.DELIVERED);
        return new ModelAndView("redirect:/restaurant="+restaurantId+"/orders");
    }


    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.GET)
    public ModelAndView editForm(@PathVariable ("restaurantId") final String restaurantIdP,
                                 @ModelAttribute("editTablesForm") final EditTablesForm form) throws Exception {

        longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("/editAvailableTables");
        Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        mav.addObject("restaurant", restaurant);

        form.setTableQty(restaurant.getTotalTables());
        form.setOpenHour(restaurant.getOpenHour());
        form.setCloseHour(restaurant.getCloseHour());
        return mav;
    }

    @RequestMapping(value = "/restaurant={restaurantId}/editTables", method = RequestMethod.POST)
    public ModelAndView editMenu(@ModelAttribute("editTablesForm") final EditTablesForm form,
                                 @PathVariable("restaurantId") final String restaurantIdP) throws Exception {

        longParser(restaurantIdP);
        long restaurantId = Long.parseLong(restaurantIdP);

        final ModelAndView mav = new ModelAndView("redirect:/restaurant=1/menu");

        rs.updateRestaurantHourAndTables(restaurantId, form.getTableQty(), form.getOpenHour(), form.getCloseHour());

        return mav;
    }



    private void longParser(Object... str) throws Exception {
        if(str.length > 0){
            try{
                Long str0 = Long.parseLong((String) str[0]);
            } catch (NumberFormatException e) {
                throw new Exception(str[0] + " is not a number");
            }
        }
        if(str.length > 1){
            try{
                Long str1 = Long.parseLong((String) str[1]);
            } catch (NumberFormatException e) {
                throw new Exception(str[1] + " is not a number");
            }
        }
        if(str.length > 2){
            try{
                Long str2 = Long.parseLong((String) str[2]);
            } catch (NumberFormatException e) {
                throw new Exception(str[2] + " is not a number");
            }
        }
    }


}
