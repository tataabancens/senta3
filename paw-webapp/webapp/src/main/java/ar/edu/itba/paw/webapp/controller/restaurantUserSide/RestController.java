package ar.edu.itba.paw.webapp.controller.restaurantUserSide;

import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.AvailableHoursDto;
import ar.edu.itba.paw.webapp.dto.RestaurantDto;
import ar.edu.itba.paw.model.exceptions.LongParseException;
import ar.edu.itba.paw.model.exceptions.NoAvailableHoursFoundException;
import ar.edu.itba.paw.model.exceptions.RestaurantNotFoundException;
import ar.edu.itba.paw.webapp.form.RestaurantPatchForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils.timestampParser;


@Path("/api/restaurants")
@Component
public class RestController {

    @Autowired
    private RestaurantService rs;

    @Autowired
    private ReservationService res;

    @Context
    private UriInfo uriInfo;

    private final static String RESTAURANT_VERSION_1 = "application/vnd.sentate.restaurant.v1+json";

    @GET
    @Path("/{id}")
    @Produces(value = { RESTAURANT_VERSION_1 })
    public Response getRestById(@PathParam("id") final long id) {
        final Optional<RestaurantDto> maybeRestaurant = rs.getRestaurantById(id).map(u -> RestaurantDto.fromRestaurant(uriInfo, u));
        if (!maybeRestaurant.isPresent()) {
            throw new RestaurantNotFoundException();
        }
        return Response.ok(maybeRestaurant.get()).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/{id}/availableHours/{date}")
    @Produces( RESTAURANT_VERSION_1 )
    public Response getAvailableHours(@PathParam("id") final long id,
                                      @PathParam("date") final String date,
                                      @DefaultValue("0")@QueryParam("qPeople") final int qPeople) throws JsonProcessingException {
        if(qPeople <= 0){
            throw new LongParseException("there must be more than 0 people"); //todo make custom exception
        }
        Optional<LocalDateTime> parsedDate = timestampParser(date);
        if(!parsedDate.isPresent()){
            throw new LongParseException("date parser error"); //todo make custom exception
        }

        List<Integer> availableHours = res.getAvailableHours(id, qPeople, parsedDate.get());
        if (availableHours.isEmpty()) {
            throw new NoAvailableHoursFoundException();
        }
        String json = objectMapper.writeValueAsString(availableHours);
        return Response.ok(AvailableHoursDto.fromHourList(availableHours)).build();
    }


    // esto creo que está de más al final
//    @GET
//    @Produces(value = {MediaType.APPLICATION_JSON, })
//    public Response getRestByUsername(@DefaultValue("")@QueryParam("username") final String username) {
//        final Optional<RestaurantDto> maybeRestaurant = rs.getRestaurantByUsername(username).map(u -> RestaurantDto.fromRestaurant(uriInfo, u));
//        if (!maybeRestaurant.isPresent()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//        return Response.ok(maybeRestaurant.get()).build();
//    }


    @PATCH
    @Consumes({ RESTAURANT_VERSION_1 })
    @Path("/{id}")
    public Response editRestaurant(@PathParam("id") final long id,
                                    final RestaurantPatchForm restaurantPatchForm){
        if(restaurantPatchForm == null) {
            return Response.status(400).build();
        }
        boolean success = rs.patchRestaurant(id, restaurantPatchForm.getRestaurantName(), restaurantPatchForm.getPhone(), restaurantPatchForm.getMail(), restaurantPatchForm.getTotalChairs(), restaurantPatchForm.getOpenHour(), restaurantPatchForm.getCloseHour(), restaurantPatchForm.getPointsForDiscount());
        if(success) {
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
    }
}
