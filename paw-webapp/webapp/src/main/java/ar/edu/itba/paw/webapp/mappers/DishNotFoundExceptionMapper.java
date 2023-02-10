package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.DishNotFoundException;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DishNotFoundExceptionMapper implements ExceptionMapper<DishNotFoundException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(DishNotFoundException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
