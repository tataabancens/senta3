package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.auth.models.ApiError;
import ar.edu.itba.paw.webapp.dto.ValidationErrorDto;
import ar.edu.itba.paw.webapp.mappers.utils.ResponseExceptionMapperUtil;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException e) {
        final List<ValidationErrorDto> errors = e.getConstraintViolations()
                .stream().map(ValidationErrorDto::fromValidationException).collect(Collectors.toList());

        List<ApiError> apiErrors = new ArrayList<>();
        for(ValidationErrorDto err : errors) {
            apiErrors.add(new ApiError(err.getField(), err.getMessage()));
        }

        return ResponseExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, "Validation error", uriInfo, apiErrors);
    }
}
