package ar.edu.itba.paw.webapp.controller.restaurantUserSide;


import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.DishService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.dto.DishDto;
import ar.edu.itba.paw.webapp.form.EditDishForm;
import ar.edu.itba.paw.webapp.form.customValidator.DeleteCategoryConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("restaurants/{restaurantId}/dishes")
@Component
public class DishController {
    @Autowired
    private RestaurantService rs;
    @Autowired
    private DishService ds;
    @Autowired
    private ImageService ims;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getDishById(@PathParam("restaurantId") final long restaurantId, @PathParam("id") final long dishId) {

        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishDto> maybeDish = ds.getDishById(dishId).map(u -> DishDto.fromDish(uriInfo, u));
        if (!maybeRestaurant.isPresent() || !maybeDish.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeDish.get()).build();
    }

    @GET
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getDishes(@PathParam("restaurantId") final long restaurantId, @QueryParam("dishCategory") final String dishCategory) {

        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishCategory> maybeCategory = rs.getDishCategoryByName(dishCategory);
        if (!maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(!maybeCategory.isPresent()){
            final List<DishDto> dishList = maybeRestaurant.get().getDishes()
                    .stream().map(dish -> DishDto.fromDish(uriInfo, dish)).collect(Collectors.toList());
            if(dishList.isEmpty()){
                return Response.noContent().build();
            }
            return Response.ok(new GenericEntity<List<DishDto>>(dishList){}).build();
        }
        final List<DishDto> dishList = maybeRestaurant.get().getDishesByCategory(maybeCategory.get())
                .stream().map(dish -> DishDto.fromDish(uriInfo, dish)).collect(Collectors.toList());;
        if(dishList.isEmpty()){
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<DishDto>>(dishList){}).build();
    }

    @POST
    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response createDish(@PathParam("restaurantId") final long restaurantId, @Valid EditDishForm form) {

        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishCategory> maybeCategory = rs.getDishCategoryByName(form.getCategory());
        if (!maybeRestaurant.isPresent() || !maybeCategory.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Dish dish = rs.createDish(maybeRestaurant.get()
                , form.getDishName(), form.getDishDesc()
                , Double.parseDouble(form.getDishPrice()),0, maybeCategory.get());

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(dish.getId())).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response deleteDish(@PathParam("restaurantId") final long restaurantId, @PathParam("id") final long dishId){
        ds.deleteDish(dishId);
        return Response.noContent().build();
    }
}
