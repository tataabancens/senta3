package ar.edu.itba.paw.model;

import java.util.List;

public class Restaurant {
    private long id;
    private String restaurantName;
    private String phone;
    private String mail;
    private List<Dish> dishes;
    private int totalTables;
    private int openHour;
    private int closeHour;

    public Restaurant(long id, String restaurantName, String phone, String mail, int totalTables, int openHour, int closeHour) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.mail = mail;
        this.totalTables = totalTables;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public long getId() {
        return id;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public int getTotalTables() {
        return totalTables;
    }

    public int getOpenHour() {
        return openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }
}
