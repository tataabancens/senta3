package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.DateFormatException;
import ar.edu.itba.paw.model.exceptions.DishCategoryNotFoundException;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DateFormatExceptionMapper implements ExceptionMapper<DateFormatException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(DateFormatException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
