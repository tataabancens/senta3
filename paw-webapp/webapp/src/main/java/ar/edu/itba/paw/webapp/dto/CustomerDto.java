package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Reservation;

import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class CustomerDto {
    private String name;


    private String phone;
    private String mail;

    private URI points;
    private URI user;
    private URI reservations;
    private URI self;

    private long id;


    public static CustomerDto fromCustomer(final UriInfo uriInfo, Customer customer) {
        final CustomerDto dto = new CustomerDto();

        dto.id = customer.getId();
        dto.name = customer.getCustomerName();
        dto.mail = customer.getMail();
        dto.phone = customer.getPhone();
        dto.points = uriInfo.getAbsolutePathBuilder()
                .path("points").build();
        dto.self = uriInfo.getAbsolutePathBuilder().build();
        if(customer.getUser() != null){
            dto.user = uriInfo.getBaseUriBuilder()
                    .path("api")
                    .path("users").path(String.valueOf(customer.getUser().getId())).build();
        } else {
            dto.user = URI.create("null");
        }

        dto.reservations = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("reservations").queryParam("customerId", customer.getId()).build();
        return dto;
    }


    public CustomerDto() {
        // para Jersey
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public URI getPoints() {
        return points;
    }

    public void setPoints(URI points) {
        this.points = points;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public URI getReservations() {
        return reservations;
    }

    public void setReservations(URI reservations) {
        this.reservations = reservations;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public long getId() {
        return id;
    }

    public void setId(long customerId) {
        this.id = customerId;
    }
}
