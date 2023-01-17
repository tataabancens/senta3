package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.webapp.form.CustomerRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Optional;


public class CustomerDto {
    private String customerName;


    private String phone;
    private String mail;
    private int points;

    private URI user;
    private URI reservations;
    private URI self;


    public static CustomerDto fromCustomer(final UriInfo uriInfo, Customer customer) {
        final CustomerDto dto = new CustomerDto();

        dto.customerName = customer.getCustomerName();
        dto.mail = customer.getMail();
        dto.phone = customer.getPhone();
        dto.points = 0;
        dto.self = uriInfo.getAbsolutePathBuilder()
                .replacePath("customers").path(String.valueOf(customer.getId())).build();
        if(customer.getUser() != null){
            dto.user = uriInfo.getAbsolutePathBuilder()
                    .replacePath("users").path(String.valueOf(customer.getUser().getId())).build();
        } else {
            dto.user = uriInfo.getAbsolutePathBuilder()
                    .replacePath("users").path(String.valueOf(customer.getUser())).build(); //TODO : bug getUser returning null
        }

//        dto.reservations = uriInfo.getAbsolutePathBuilder()
//                .replacePath("customers").path(String.valueOf(customer.getReservations()))).build(); //TODO
        return dto;
    }


    public CustomerDto() {
        // para Jersey
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
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
}
