package ar.edu.itba.paw.webapp.mappers.utils;

import ar.edu.itba.paw.webapp.auth.models.ApiErrorDetails;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ResponseExceptionMapperUtil {
    private ResponseExceptionMapperUtil() {

    }

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo) {
        ApiErrorDetails apiErrorDetails = new ApiErrorDetails();
        apiErrorDetails.setStatus(status.getStatusCode());
        apiErrorDetails.setTitle(status.getReasonPhrase());
        apiErrorDetails.setMessage(message);
        apiErrorDetails.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(apiErrorDetails).type("application/vnd.api.v1+json").build();
    }
}
