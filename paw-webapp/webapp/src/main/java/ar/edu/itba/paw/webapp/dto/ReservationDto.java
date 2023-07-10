package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Customer;
import ar.edu.itba.paw.model.Dish;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class ReservationDto {

    private String securityCode;
    private int hour;
    private String date;
    private String customerName;

    private int peopleAmount;
    private int tableNumber;
    private boolean hand;
    private boolean usedDiscount;
    private String status;

    private URI orderItems;
    private URI restaurant;
    private URI customer;
    private URI recommendedDish;

    public static ReservationDto fromReservation(final UriInfo uriInfo, Reservation reservation) {
        final ReservationDto dto = new ReservationDto();

        dto.securityCode = reservation.getSecurityCode();
        dto.hour = reservation.getReservationHour();
        dto.date = reservation.getReservationDate().toString().substring(0, 10);
        dto.peopleAmount = reservation.getqPeople();
        dto.tableNumber = reservation.getTableNumber();
        dto.hand = reservation.isHand();
        dto.usedDiscount = reservation.isReservationDiscount();
        dto.customerName = reservation.getCustomer().getCustomerName();
        dto.status = reservation.getReservationStatus().getName();

        dto.orderItems = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("reservations").path(String.valueOf(reservation.getSecurityCode())).path("orderItems").build();

        dto.restaurant = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("restaurants").path(String.valueOf(reservation.getRestaurant().getId())).build();

        dto.customer = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("customers").path(String.valueOf(reservation.getCustomer().getId())).build();
        return dto;
    }


    public ReservationDto() {
        // para Jersey
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public URI getCustomer() {
        return customer;
    }

    public void setCustomer(URI customer) {
        this.customer = customer;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getPeopleAmount() {
        return peopleAmount;
    }

    public void setPeopleAmount(int peopleAmount) {
        this.peopleAmount = peopleAmount;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isHand() {
        return hand;
    }

    public void setHand(boolean hand) {
        this.hand = hand;
    }

    public URI getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(URI orderItems) {
        this.orderItems = orderItems;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setRecommendedDish(URI recommendedDish) {
        this.recommendedDish = recommendedDish;
    }

    public void setRecommendedDishFromOut(UriInfo uriInfo, Dish dish) {
        this.recommendedDish = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("dishes").path(String.valueOf(dish.getId())).build();
    }
    public URI getRecommendedDish() {
        return recommendedDish;
    }

    public boolean isUsedDiscount() {
        return usedDiscount;
    }

    public void setUsedDiscount(boolean usedDiscount) {
        this.usedDiscount = usedDiscount;
    }
}
