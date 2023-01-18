package ar.edu.itba.paw.webapp.controller.customerUserSide;

// THIS IS A SOTUYO FILE

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.enums.Roles;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService us;
    @Context
    private UriInfo uriInfo;
//    @GET
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response listUsers() {
//        final List<User> allUsers = us.getAll();
//        return Response.ok(new UserList(allUsers)).build();
//    }

    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @POST
    public Response createUser(@Valid final CreateUserForm createUserForm) {
        final User user = us.create(createUserForm.getUsername(), "123", Roles.valueOf(createUserForm.getRole()));
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final Optional<User> maybeUser = us.getUserByID(id);
        if (maybeUser.isPresent()) {
            return Response.ok(UserDto.fromUser(maybeUser.get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response deleteById(@PathParam("id") final long id) {
        us.deleteById(id);
        return Response.noContent().build();
    }
}