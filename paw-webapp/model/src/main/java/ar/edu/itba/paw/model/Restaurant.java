package ar.edu.itba.paw.model;

import java.util.List;

public class Restaurant {
    private long id;
    private String restaurantName;
    private String phone;
    private String mail;
    private List<Dish> dishes;
    private int tables;

    public Restaurant(long id, String username, String phone, String mail, int tables) {
        super();
        this.id = id;
        this.restaurantName = username;
        this.phone = phone;
        this.mail = mail;
        this.tables = tables;
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

    public int getTables() {
        return tables;
    }
}
