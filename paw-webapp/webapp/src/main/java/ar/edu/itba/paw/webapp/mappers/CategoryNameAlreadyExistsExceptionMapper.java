package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.CategoryNameAlreadyExistsException;
import ar.edu.itba.paw.model.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;

public class CategoryNameAlreadyExistsExceptionMapper implements ExceptionMapper<CategoryNameAlreadyExistsException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(CategoryNameAlreadyExistsException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
