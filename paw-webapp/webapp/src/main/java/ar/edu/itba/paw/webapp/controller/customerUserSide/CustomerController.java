package ar.edu.itba.paw.webapp.controller.customerUserSide;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.webapp.annotations.PATCH;
import ar.edu.itba.paw.webapp.dto.CustomerDto;
import ar.edu.itba.paw.webapp.dto.ReservationDto;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.form.CustomerPatchForm;
import ar.edu.itba.paw.webapp.form.CustomerRegisterForm;
import ar.edu.itba.paw.webapp.mappers.CustomerNotFoundExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("customers")
@Component
public class CustomerController {

    @Autowired
    private CustomerService cs;

//    @Autowired
//    private ReservationService rs;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCustomerById(@PathParam("id") final long id){
        final Optional<CustomerDto> maybeCustomer = cs.getCustomerById(id).map(c -> CustomerDto.fromCustomer(uriInfo, c));
        if(!maybeCustomer.isPresent()){
            throw new CustomerNotFoundException();
        } else {
            return Response.ok(maybeCustomer.get()).build();
        }
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response getCustomers(@DefaultValue("1")@QueryParam("page") final int page){
                        //        @DefaultValue("")@QueryParam("username") final String username,
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



    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @POST
    public Response CreateCustomer(@Valid final CustomerRegisterForm customerForm) {
        final Customer newCustomer = cs.create(customerForm.getCustomerName(), customerForm.getPhone(), customerForm.getMail());

        final URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(newCustomer.getId())).build();
        return Response.created(location).build();
    }


    @PATCH
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/{id}")
    public Response EditCustomer(@PathParam("id") final long id,
                                 final CustomerPatchForm customerPatchForm){
        if(customerPatchForm == null){
            return Response.status(400).build();
        }
        boolean success = cs.patchCustomer(id, customerPatchForm.getName(), customerPatchForm.getPhone(), customerPatchForm.getMail(), customerPatchForm.getUserId(), customerPatchForm.getPoints());
        if(success)
            return Response.ok().build();
        return Response.status(400).build();
    }

    //todo doing
    @DELETE
    @Path("/{id}")
    public Response DeleteCustomer(@PathParam("id") final long id){
        boolean success = cs.deleteCustomer(id);
        if (success){
            return Response.ok().build();
        }
        return Response.status(400).build();
    }

}
