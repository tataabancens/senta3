package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.CustomerDto;
import ar.edu.itba.paw.model.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.dto.PointsDto;
import ar.edu.itba.paw.webapp.form.CustomerPatchForm;
import ar.edu.itba.paw.webapp.form.CustomerPointsPatchForm;
import ar.edu.itba.paw.webapp.form.CustomerRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/customers")
@Component
public class CustomerController {

    @Autowired
    private CustomerService cs;

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    private final static String CUSTOMER_VERSION_1 = "application/vnd.sentate.customer.v1+json";

    @GET
    @Path("/{id}")
    @Produces(value = { CUSTOMER_VERSION_1 })
    public Response getCustomerById(@PathParam("id") final long id){
        final Optional<CustomerDto> maybeCustomer = cs.getCustomerById(id).map(c -> CustomerDto.fromCustomer(uriInfo, c));
        if(!maybeCustomer.isPresent()){
            throw new CustomerNotFoundException();
        } else {
            return Response.ok(maybeCustomer.get()).build();
        }
    }

    @GET
    @Produces(value = { CUSTOMER_VERSION_1 })
    public Response getCustomers(@DefaultValue("1")@QueryParam("page") final int page){
        final List<CustomerDto> customers = cs.getCustomers(page)
                .stream()
                .map(c -> CustomerDto.fromCustomer(uriInfo, c))
                .collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<CustomerDto>>(customers) {})
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page-1).build(), "prev")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", page+1).build(), "next")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first")
//                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", 999).build(), "last") //TODO
                .build();
    }



    @Consumes({ CUSTOMER_VERSION_1 })
    @POST
    public Response CreateCustomer(@Valid final CustomerRegisterForm customerForm) {
        final Customer newCustomer = cs.create(customerForm.getCustomerName(), customerForm.getPhone(), customerForm.getMail(), us.getUserByID(customerForm.getUserId()));

        if(newCustomer==null){
            return Response.status(400).build();  //userId already has a customer
        }
        final URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newCustomer.getId())).build();
        return Response.created(location).build();
    }


    @PATCH
    @Consumes({ CUSTOMER_VERSION_1 })
    @Path("/{id}")
    public Response EditCustomer(@PathParam("id") final long id,
                                 final CustomerPatchForm customerPatchForm){
        if(customerPatchForm == null){
            return Response.status(400).build();
        }
        boolean success = cs.patchCustomer(id, customerPatchForm.getName(), customerPatchForm.getPhone(), customerPatchForm.getMail(), customerPatchForm.getUserId());
        if(success)
            return Response.ok().build();
        return Response.status(400).build();
    }

    @GET
    @Path("/{id}/points")
    @Produces(value = { CUSTOMER_VERSION_1 })
    public Response getCustomerPointsById(@PathParam("id") final long id){
        final Optional<PointsDto> maybePoints = cs.getCustomerById(id).map(c -> PointsDto.fromCustomer(uriInfo, c));
        if(!maybePoints.isPresent()){
            throw new CustomerNotFoundException();
        } else {
            return Response.ok(maybePoints.get()).build();
        }
    }

    @PATCH
    @Consumes({ CUSTOMER_VERSION_1 })
    @Path("/{id}/points")
    public Response EditPoints(@PathParam("id") final long id,
                                 final @Valid CustomerPointsPatchForm pointsPatchForm){
        if(pointsPatchForm == null){
            return Response.status(400).build();
        }
        boolean success = cs.patchCustomerPoints(id, pointsPatchForm.getPoints());
        if(success)
            return Response.ok().build();
        return Response.status(400).build();
    }
}
