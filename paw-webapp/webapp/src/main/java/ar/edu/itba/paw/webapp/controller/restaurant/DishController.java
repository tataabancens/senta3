package ar.edu.itba.paw.webapp.controller.restaurant;


import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.DishService;
import ar.edu.itba.paw.service.ImageService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.DishDto;
import ar.edu.itba.paw.model.exceptions.DishNotFoundException;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.DishPatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/restaurants/{restaurantId}/dishes")
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

    private final static String DISH_VERSION_1 = "application/vnd.sentate.dish.v1+json";

    @GET
    @Path("/{id}")
    @Produces(value = { DISH_VERSION_1 })
    public Response getDishById(@PathParam("restaurantId") final long restaurantId, @PathParam("id") final long dishId) {
        final Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        final Optional<DishDto> maybeDish = ds.getDishById(dishId).map(u -> DishDto.fromDish(uriInfo, u));
        if (!maybeDish.isPresent()) {
            throw new DishNotFoundException();
        }
        return Response.ok(maybeDish.get()).build();
    }

    @GET
    @Produces(value = { DISH_VERSION_1 })
    public Response getDishes(@PathParam("restaurantId") final long restaurantId,
                              @DefaultValue("")@QueryParam("dishCategory") final String dishCategory) {
        Optional<List<Dish>> maybeDishList = ds.getDishes(restaurantId, dishCategory);
        if(!maybeDishList.isPresent()){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        List<DishDto> dishDtoList = maybeDishList.get()
                .stream().map(dish -> DishDto.fromDish(uriInfo, dish)).collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<DishDto>>(dishDtoList){}).build();
    }

    @POST
    @Consumes({ DISH_VERSION_1 })
    public Response createDish(@PathParam("restaurantId") final long restaurantId, @Valid DishPatchForm form) {
        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishCategory> maybeCategory = rs.getDishCategoryById(form.getCategoryId());
        if (!maybeRestaurant.isPresent() || !maybeCategory.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        final Dish dish = rs.createDish(maybeRestaurant.get()
                , form.getName(), form.getDescription()
                , form.getPrice() //, Double.parseDouble(form.getPrice())
                , form.getImageId(), maybeCategory.get());

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(dish.getId())).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = { DISH_VERSION_1 })
    public Response deleteDish(@PathParam("restaurantId") final long restaurantId, @PathParam("id") final long dishId){
        ds.deleteDish(dishId);
        return Response.noContent().build();
    }

    @PATCH
    @Consumes({ DISH_VERSION_1 })
    @Path("/{id}")
    public Response editDish(@PathParam("restaurantId") final String restaurantId,
                             @PathParam("id") final long dishId,
                             final DishPatchForm dishPatchForm){

        boolean success = ds.patchDish(dishId, dishPatchForm.getName(), dishPatchForm.getDescription(), dishPatchForm.getPrice(), dishPatchForm.getCategoryId(), dishPatchForm.getImageId());
        return (success) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
