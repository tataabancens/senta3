package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.RestaurantDto;
import ar.edu.itba.paw.webapp.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.RestaurantPatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;


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
            throw new RestaurantNotFoundException();
        }
        return Response.ok(maybeRestaurant.get()).build();
    }

    // esto creo que está de más al final
//    @GET
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response getRestById(@DefaultValue("")@QueryParam("username") final String username) {
//        final Optional<RestaurantDto> maybeRestaurant = rs.getRestaurantByUsername(username).map(u -> RestaurantDto.fromRestaurant(uriInfo, u));
//        if (!maybeRestaurant.isPresent()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(maybeRestaurant.get()).build();
//    }


    @PATCH
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{id}")
    public Response editRestaurant(@PathParam("id") final long id,
                                    final RestaurantPatchForm restaurantPatchForm){
        if(restaurantPatchForm == null){
            return Response.status(400).build();
        }
        boolean success = rs.patchRestaurant(id, restaurantPatchForm.getRestaurantName(), restaurantPatchForm.getPhone(), restaurantPatchForm.getMail(), restaurantPatchForm.getTotalChairs(), restaurantPatchForm.getOpenHour(), restaurantPatchForm.getCloseHour());
        if(success) {
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
    }
}
