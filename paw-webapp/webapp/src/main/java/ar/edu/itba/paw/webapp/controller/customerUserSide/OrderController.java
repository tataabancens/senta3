package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.OrderItemDto;
import ar.edu.itba.paw.webapp.exceptions.OrderItemNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ReservationNotFoundException;
import ar.edu.itba.paw.webapp.form.CreateOrderItemForm;
import ar.edu.itba.paw.webapp.form.OrderItemPatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("reservations/{securityCode}/orderItems")
@Controller
public class OrderController {
    private final RestaurantService res;
    private final ReservationService rs;
    private final DishService ds;
    private final CustomerService cs;

    @Context
    private UriInfo uriInfo;


    @Autowired
    public OrderController(final RestaurantService res, final ReservationService rs, final DishService ds, final CustomerService cs) {
        this.res = res;
        this.rs = rs;
        this.ds = ds;
        this.cs = cs;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getOrderItems(@PathParam("securityCode") final String securityCode) {
        Optional<Reservation> maybeReservation = rs.getReservationBySecurityCode(securityCode);
        if (!maybeReservation.isPresent()) {
            throw new ReservationNotFoundException();
        }
        List<OrderItemDto> orderItemDtoList = rs.getOrderItemsOfReservation(maybeReservation.get().getId())
                .stream()
                .map(o -> OrderItemDto.fromOrderItem(uriInfo, o))
                .collect(Collectors.toList());

        return Response.ok(new GenericEntity<List<OrderItemDto>>(orderItemDtoList) {}).build();
    }

    @GET
    @Path("/{orderItemId}")
    @Produces(value = {MediaType.APPLICATION_JSON,})
    public Response getOrderItems(@PathParam("securityCode") final String securityCode,
                                  @PathParam("orderItemId") final long orderItemId) {
        Reservation reservation = rs.getReservationBySecurityCode(securityCode).orElseThrow(ReservationNotFoundException::new);
        Optional<OrderItemDto> maybeOrderItem = rs.getOrderItemById(orderItemId).map(o -> OrderItemDto.fromOrderItem(uriInfo, o));

        if (!maybeOrderItem.isPresent()) {
            throw new OrderItemNotFoundException();
        }

        return Response.ok(maybeOrderItem.get()).build();
    }

    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @POST
    public Response createOrderItem(@PathParam("securityCode") final String securityCode,
                                    @Valid final CreateOrderItemForm orderItemForm) {
        final OrderItem newOrderItem = rs.createOrderItemPost(securityCode, orderItemForm.getDishId(), orderItemForm.getQuantity());
        if(null == newOrderItem){
            return Response.status(400).build();
        }
        final URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newOrderItem.getId())).build();
        return Response.created(location).build();
    }

    @PATCH
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{id}")
    public Response editReservation(@PathParam("securityCode") final String securityCode,
                                    @PathParam("id") final long id,
                                    final OrderItemPatchForm orderItemPatchForm){

        return (rs.patchOrderItem(securityCode, id, orderItemPatchForm.getStatus())) ? Response.ok().build() : Response.status(400).build();
    }
}