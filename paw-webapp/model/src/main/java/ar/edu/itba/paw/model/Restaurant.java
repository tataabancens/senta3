package ar.edu.itba.paw.model;

public class Restaurant {
    private long id;
    private String restaurantName;
    private String phone;
    private String mail;

    public Restaurant(long id, String username, String phone, String mail) {
        super();
        this.id = id;
        this.restaurantName = username;
        this.phone = phone;
        this.mail = mail;
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
}
