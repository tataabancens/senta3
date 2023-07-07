package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Customer;
import org.glassfish.jersey.internal.inject.Custom;

import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.net.URI;

public class PointsDto {
    private int points;
    private URI self;
    private URI customer;

    PointsDto() {

    }

    public static PointsDto fromCustomer(UriInfo uriInfo, Customer customer) {
        PointsDto dto = new PointsDto();

        dto.points = customer.getPoints();
        dto.self = uriInfo.getAbsolutePathBuilder().build();
        dto.customer = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("customers")
                .path(String.valueOf(customer.getId())).build();
        return dto;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getCustomer() {
        return customer;
    }

    public void setCustomer(URI customer) {
        this.customer = customer;
    }
}
