package ar.edu.itba.paw.webapp.controller.customerUserSide;


import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.DishService;
import ar.edu.itba.paw.model.exceptions.DateFormatException;
import ar.edu.itba.paw.service.ReservationService;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.DishDto;
import ar.edu.itba.paw.webapp.dto.ReservationDto;
import ar.edu.itba.paw.model.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.form.CreateReservationForm;
import ar.edu.itba.paw.webapp.form.ReservationPatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.webapp.controller.utilities.ControllerUtils.timestampParser;

@Path("/api/reservations")
@Component
public class ReservationController {

    @Autowired
    private ReservationService rs;
    @Autowired
    private DishService ds;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("{securityCode}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getReservationBySecurityCode(@PathParam("securityCode") final String securityCode){
        final Optional<ReservationDto> maybeReservation = rs.getReservationBySecurityCode(securityCode).map(r -> ReservationDto.fromReservation(uriInfo, r));
        if(!maybeReservation.isPresent()){
            throw new ReservationNotFoundException();
        } else {
            return Response.ok(maybeReservation.get()).build();
        }
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getReservationsOrderedBy(@DefaultValue("1") @QueryParam("restaurantId") final long restaurantId,
                                                 @DefaultValue("0") @QueryParam("customerId") final long customerId,
                                                 @DefaultValue("reservationid")@QueryParam("orderBy") final String orderBy,
                                                 @DefaultValue("ASC")@QueryParam("direction") final String direction,
                                                 @DefaultValue("9")@QueryParam("filterStatus") final String filterStatus,
                                                 @DefaultValue("1")@QueryParam("page") final int page){
        final List<ReservationDto> reservationsList = rs.getAllReservationsOrderedBy(restaurantId, orderBy, direction, filterStatus, page, customerId)
                .stream()
                .map(r -> ReservationDto.fromReservation(uriInfo, r))
                .collect(Collectors.toList());
        if(reservationsList.isEmpty()){
            return Response.noContent().build();
        }
        int lastPage = rs.getTotalPages(restaurantId, filterStatus, customerId);

        return Response.ok(new GenericEntity<List<ReservationDto>>(reservationsList) {})
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page-1).build(), "prev")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page+1).build(), "next")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last")
                .build();
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createReservation(@Valid final CreateReservationForm reservationForm,
                                      @Context  HttpServletRequest request) {
        Optional<LocalDateTime> maybeDate = timestampParser(reservationForm.getReservationDate());
        if(!maybeDate.isPresent()) {
            throw new DateFormatException();
        }
        long customerId = Long.parseLong(request.getHeader("customerId"));
        final Reservation newReservation = rs.createReservationPost(reservationForm.getRestaurantId(), customerId, reservationForm.getHour(), reservationForm.getqPeople(), LocalDateTime.now(), maybeDate.get());
        if(null == newReservation) {
            return Response.status(400).build(); //Bad request, //informar cual error
        }
        final URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newReservation.getSecurityCode())).build();
        return Response.created(location).build();
    }



    @PATCH
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{securityCode}")
    public Response editReservation(@PathParam("securityCode") final String securityCode,
                                    final ReservationPatchForm reservationPatchForm){
        boolean success = rs.patchReservation(securityCode, reservationPatchForm.getReservationDate(), reservationPatchForm.getHour(), reservationPatchForm.getqPeople(), reservationPatchForm.getTable(), reservationPatchForm.isHand(), reservationPatchForm.isDiscount(), reservationPatchForm.getReservationStatus());
        if(success) {
            return Response.ok().build();
        } else {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/{securityCode}")
    public Response deleteReservation(@PathParam("securityCode") final String securityCode){
        boolean success = rs.cancelReservation(securityCode);
        if(success){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

    @Path("/{securityCode}/recommendedDish")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getRecommendedDish(@PathParam("securityCode") final String securityCode){
        Optional<DishDto> recommendedDish = ds.getRecommendedDish(securityCode).map(d -> DishDto.fromDish(uriInfo, d));
        if(!recommendedDish.isPresent()){
            return Response.noContent().build();
        }
        return Response.ok(recommendedDish.get()).build();
    }


}
