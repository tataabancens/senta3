package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_restaurantid_seq")
    @SequenceGenerator(sequenceName = "restaurant_restaurantid_seq", name = "restaurant_restaurantid_seq", allocationSize = 1)
    @Column(name = "restaurantid")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String restaurantName;

    @Column(length = 100, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String mail;

    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private List<Dish> dishes;

    @Column(nullable = false)
    private Integer totalChairs;

    @Column(nullable = false)
    private Integer openHour;

    @Column(nullable = false)
    private Integer closeHour;

    /* default */ Restaurant() {
        // Just for hibernate
    }

    public Restaurant(String restaurantName, String phone, String mail, int totalChairs, int openHour, int closeHour) {
        super();
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.mail = mail;
        this.totalChairs = totalChairs;
        this.openHour = openHour;
        this.closeHour = closeHour;
    }

    @Deprecated
    public Restaurant(long id, String restaurantName, String phone, String mail, int totalChairs, int openHour, int closeHour) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.phone = phone;
        this.mail = mail;
        this.totalChairs = totalChairs;
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

    public int getTotalChairs() {
        return totalChairs;
    }

    public int getOpenHour() {
        return openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }
}
