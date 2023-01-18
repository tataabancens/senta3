package ar.edu.itba.paw.webapp.controller.restaurantUserSide;


import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.dto.DishCategoryDto;
import ar.edu.itba.paw.webapp.dto.DishDto;
import ar.edu.itba.paw.webapp.dto.RestaurantDto;
import ar.edu.itba.paw.webapp.form.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("restaurants")
@Component
public class RestController {

    @Autowired
    private RestaurantService rs;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getRestById(@PathParam("id") final long id) {
        final Optional<RestaurantDto> maybeRestaurant = rs.getRestaurantById(id).map(u -> RestaurantDto.fromRestaurant(uriInfo, u));
        if (!maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(maybeRestaurant.get()).build();
    }

    @GET
    @Path("/{restaurantId}/dishCategories/{categoryId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getDishCategoryById(@PathParam("restaurantId") final long restaurantId
                                        , @PathParam("categoryId") final long categoryId){
        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishCategory> maybeDishCategory = rs.getDishCategoryById(categoryId);
        if (!maybeRestaurant.isPresent() || !maybeDishCategory.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        DishCategoryDto dishCategoryDto = DishCategoryDto.fromDishCategory(uriInfo, maybeDishCategory.get());
        return Response.ok(dishCategoryDto).build();
    }

    @GET
    @Path("/{restaurantId}/dishCategories")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getDishCategories(@PathParam("restaurantId") final long restaurantId){
        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);

        if (!maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<DishCategoryDto> categoryList = maybeRestaurant.get().getDishCategories()
                .stream().map(dishCategory -> DishCategoryDto.fromDishCategory(uriInfo, dishCategory))
                .collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<DishCategoryDto>>(categoryList){}).build();
    }

    @POST
    @Path("/{restaurantId}/dishCategories")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response creatCategory(@PathParam("restaurantId")final long restaurantId,
                                  @Valid CategoryForm categoryForm){

        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        if (!maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        DishCategory dishCategory = rs.createDishCategory(maybeRestaurant.get(), categoryForm.getCategoryName());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(dishCategory.getId())).build();

        return Response.created(uri).build();
    }

    /*@PUT
    @Path("/{restaurantId}/dishCategories/{categoryId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response editCategory(@PathParam("restaurantId") final long restaurantId,
                                   @PathParam("categoryId") final long categoryId){
        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        final Optional<DishCategory> maybeCategory = rs.getDishCategoryById(categoryId);
        if (!maybeRestaurant.isPresent() || !maybeCategory.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }*/

    @DELETE
    @Path("/{restaurantId}/dishCategories/{categoryId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response deleteCategory(@PathParam("restaurantId") final long restaurantId,
                                   @PathParam("categoryId") final long categoryId){
        final Optional<Restaurant> maybeRestaurant = rs.getRestaurantById(restaurantId);
        if (!maybeRestaurant.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        rs.deleteCategory(maybeRestaurant.get(), categoryId);

        return Response.noContent().build();
    }
}
