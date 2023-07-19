package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.NoAvailableHoursFoundException;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoAvailableHoursExceptionMapper implements ExceptionMapper<NoAvailableHoursFoundException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NoAvailableHoursFoundException e) {
        return ResponseExceptionMapperUtil.toResponse(Response.Status.NO_CONTENT, e.getMessage(), uriInfo);
    }
}