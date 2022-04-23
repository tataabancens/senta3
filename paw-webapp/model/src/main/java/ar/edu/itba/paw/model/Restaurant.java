package ar.edu.itba.paw.model;

import java.util.List;

public class Restaurant {
    private long id;
    private String restaurantName;
    private String phone;
    private String mail;
    private List<Dish> dishes;
    private int totalTables;

    public Restaurant(long id, String username, String phone, String mail, int totalTables) {
        super();
        this.id = id;
        this.restaurantName = username;
        this.phone = phone;
        this.mail = mail;
        this.totalTables = totalTables;
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
}
