package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.model.DishCategory;
import ar.edu.itba.paw.model.Restaurant;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.DishCategoryDto;
import ar.edu.itba.paw.model.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
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

@Path("/api/restaurants/{restaurantId}/dishCategories")
@Component
public class DishCategoryController {

    @Autowired
    private RestaurantService rs;

    @Context
    private UriInfo uriInfo;

    private final static String DISH_CATEGORY_VERSION_1 = "application/vnd.sentate.dish_category.v1+json";


    @GET
    @Produces(value = {DISH_CATEGORY_VERSION_1})
    public Response getDishCategories(@PathParam("restaurantId") final long restaurantId){
        final Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);

        List<DishCategoryDto> categoryList = restaurant.getDishCategories()
                .stream().map(dishCategory -> DishCategoryDto.fromDishCategory(uriInfo, dishCategory))
                .collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<DishCategoryDto>>(categoryList){}).build();
    }

    @GET
    @Path("/{categoryId}")
    @Produces(value = { DISH_CATEGORY_VERSION_1 })
    public Response getDishCategoryById(@PathParam("restaurantId") final long restaurantId
            , @PathParam("categoryId") final long categoryId){
        final Restaurant restaurant = rs.getRestaurantById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        final DishCategory dishCategory = rs.getDishCategoryById(categoryId).orElseThrow(DishCategoryNotFoundException::new);

        DishCategoryDto dishCategoryDto = DishCategoryDto.fromDishCategory(uriInfo, dishCategory);
        return Response.ok(dishCategoryDto).build();
    }

    @POST
    @Consumes({ DISH_CATEGORY_VERSION_1 })
    public Response createCategory(@PathParam("restaurantId")final long restaurantId,
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


    @PATCH
    @Path("/{categoryId}")
    @Produces(value = { DISH_CATEGORY_VERSION_1 })
    public Response editCategory(@PathParam("restaurantId") final long restaurantId,
                                 @PathParam("categoryId") final long categoryId,
                                 final CategoryForm dishCategoryPatchForm){

        boolean success = rs.patchDishCategory(restaurantId, categoryId, dishCategoryPatchForm.getCategoryName());
        return (success) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{categoryId}")
    @Produces(value = { DISH_CATEGORY_VERSION_1 })
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
