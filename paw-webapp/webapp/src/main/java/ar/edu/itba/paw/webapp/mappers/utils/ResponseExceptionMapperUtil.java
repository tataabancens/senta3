package ar.edu.itba.paw.webapp.mappers.utils;

import ar.edu.itba.paw.webapp.auth.models.ApiError;
import ar.edu.itba.paw.webapp.auth.models.ApiErrorDetails;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public final class ResponseExceptionMapperUtil {
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

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo, List<ApiError> errorList) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());
        errorDetails.setErrors(errorList);
        return Response.status(status).entity(errorDetails).type("application/vnd.api.v1+json").build();
    }
}
