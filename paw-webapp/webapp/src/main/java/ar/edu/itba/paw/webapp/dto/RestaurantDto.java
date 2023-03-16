package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Restaurant;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class RestaurantDto {
    private String name;
    private String phone;
    private String mail;
    private URI self;
    private URI dishes;
    private URI reservations;
    private URI dishCategories;
    private URI user;
    private int totalChairs;
    private int openHour, closeHour;
    private long id;

    public RestaurantDto() {
        // for jersey
    }

    public static RestaurantDto fromRestaurant(final UriInfo uriInfo, Restaurant res) {
        RestaurantDto dto = new RestaurantDto();
        dto.name = res.getRestaurantName();
        dto.phone = res.getPhone();
        dto.mail = res.getMail();
        dto.totalChairs = res.getTotalChairs();
        dto.openHour = res.getOpenHour();
        dto.closeHour = res.getCloseHour();
        dto.id = res.getId();

        final UriBuilder restaurantUriBuilder = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("restaurants").path(String.valueOf(res.getId()));
        dto.self = restaurantUriBuilder.build();

        UriBuilder dishesUriBuilder = restaurantUriBuilder.clone()
                .path("dishes");
        dto.dishes = dishesUriBuilder.build();

        UriBuilder dishCategoriesBuilder = restaurantUriBuilder.clone().path("dishCategories");
        dto.dishCategories = dishCategoriesBuilder.build();

        final UriBuilder userUriBuilder = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("users").path(String.valueOf(res.getUser().getId()));
        dto.user = userUriBuilder.build();

        final UriBuilder reservationUriBuilder = uriInfo.getBaseUriBuilder()
                .path("api")
                .path("reservations");
        dto.reservations = reservationUriBuilder.build();
        return dto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getDishes() {
        return dishes;
    }

    public void setDishes(URI dishes) {
        this.dishes = dishes;
    }

    public URI getReservations() {
        return reservations;
    }

    public void setReservations(URI reservations) {
        this.reservations = reservations;
    }

    public URI getDishCategories() {
        return dishCategories;
    }

    public void setDishCategories(URI dishCategories) {
        this.dishCategories = dishCategories;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public int getTotalChairs() {
        return totalChairs;
    }

    public void setTotalChairs(int totalChairs) {
        this.totalChairs = totalChairs;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }
}
