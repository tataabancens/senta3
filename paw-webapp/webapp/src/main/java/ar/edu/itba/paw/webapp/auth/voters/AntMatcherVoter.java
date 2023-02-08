package ar.edu.itba.paw.webapp.auth.voters;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.CustomerService;
import ar.edu.itba.paw.service.RestaurantService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.exceptions.CustomerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ar.edu.itba.paw.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Component
public class AntMatcherVoter {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private CustomerService customerService;



    private long getUserId(Authentication authentication){
        return getUser(authentication).getId();
    }

    private User getUser(Authentication authentication){
        Optional<User> asd = userService.getUserByUsername((authentication).getName());
        return userService.getUserByUsername((authentication).getName()).orElseThrow(UserNotFoundException::new);
    }

    public boolean canAccessReservation(Authentication authentication, String securityCode) {
        if(isRestaurant(authentication)) return true;
        Optional<Reservation> res = reservationService.getReservationBySecurityCode(securityCode);
        if(!res.isPresent()) return false;
        if(res.get().getCustomer().getUser() == null) return true; //reservation has no user
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        return res.get().getCustomer().getUserId() == getUserId(authentication);
    }

    public boolean canPostReservation(Authentication authentication){
        return true; //todo
    }

    private long getCustomerId(Authentication authentication){
        return getCustomer(authentication).getId();
    }

    private Customer getCustomer(Authentication authentication){
        return customerService.getCustomerByUsername((authentication).getName()).orElseThrow(CustomerNotFoundException::new);
    }

    public boolean canAccessUser(Authentication authentication, long id){
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        Optional<User> requestedUser = userService.getUserByID(id);
        return requestedUser.isPresent() && Objects.equals(requestedUser.get().getUsername(), authentication.getName());
    }

    public boolean canAccessOrderItem(Authentication authentication, String securityCode, long orderItemId) {
        if(isRestaurant(authentication)) return true;
        Optional<Reservation> requestedRes = reservationService.getReservationBySecurityCode(securityCode);
        Optional<OrderItem> requestedOI = reservationService.getOrderItemById(orderItemId);
        if(!requestedRes.isPresent() || !requestedOI.isPresent()) return false;
        if(!Objects.equals(requestedOI.get().getReservation().getSecurityCode(), requestedRes.get().getSecurityCode())) return false;
        if(authentication instanceof AnonymousAuthenticationToken){
            return requestedRes.get().getCustomer().getUser() == null;
        }
        return Objects.equals(authentication.getName(), requestedRes.get().getCustomer().getUser().getUsername());
    }

    public boolean canAccessOrderItems(Authentication authentication, String securityCode) {
        if(isRestaurant(authentication)) return true;
        Optional<Reservation> requestedReservation = reservationService.getReservationBySecurityCode(securityCode);
        if(!requestedReservation.isPresent()) return false;
        if(authentication instanceof AnonymousAuthenticationToken){
            return requestedReservation.get().getCustomer().getUser() == null;
        }
        return Objects.equals(requestedReservation.get().getCustomer().getUser().getUsername(), authentication.getName());
    }

    private boolean isRestaurant(Authentication authentication){
        return Objects.equals(getUser(authentication).getRole(), "ROLE_RESTAURANT");
    }

    public boolean canAccessReservationList(Authentication authentication, String query){
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        if(isRestaurant(authentication)) return true;
        int startIndex = query.indexOf("customerId=");
        if (startIndex != -1) {
            startIndex += 11; //length of "customerId="
            int amperIndex = query.indexOf("&", startIndex);
            int endIndex = query.length();
            int endSubstring = amperIndex>0 ? amperIndex : endIndex;
            String requestedCustomerId = query.substring(startIndex, endSubstring);
            return String.valueOf(getCustomerId(authentication)).equals(requestedCustomerId);
        }
        return false;
    }

    public boolean canAccessCustomer(Authentication authentication, long customerId) {
        if(isRestaurant(authentication)) return true;
        Optional<Customer> requestedCustomer = customerService.getCustomerById(customerId);
        if(!requestedCustomer.isPresent()) return false;
        if(requestedCustomer.get().getUser() == null) return true; //there is no user for this customer
        if (authentication instanceof AnonymousAuthenticationToken) return false; //there is a user, but no log in was provided
        long requesterId = getCustomerId(authentication);
        if(requesterId == customerId) return true;
        return requestedCustomer.get().getUserId() == getUserId(authentication);
    }
}
