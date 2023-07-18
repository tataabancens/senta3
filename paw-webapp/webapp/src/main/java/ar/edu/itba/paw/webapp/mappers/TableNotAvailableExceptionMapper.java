package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.TableNotAvailableException;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TableNotAvailableExceptionMapper implements ExceptionMapper<TableNotAvailableException>{
        @Context
        private UriInfo uriInfo;

        @Override
        public Response toResponse(TableNotAvailableException e) {
            return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
        }
}
