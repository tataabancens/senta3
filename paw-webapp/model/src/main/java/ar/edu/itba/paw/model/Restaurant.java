package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.enums.DishCategory;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant", orphanRemoval = true)
    private List<Dish> dishes;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "userid")
    private User user;

    @Column(nullable = false)
    private Integer totalChairs;

    @Column(nullable = false)
    private Integer openHour;

    @Column(nullable = false)
    private Integer closeHour;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

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
    public Dish createDish(String dishName, String dishDescription, double price, long imageId, DishCategory category) {
        Dish dish = new Dish(this, dishName, (int) price,  dishDescription, imageId, category);
        dishes.add(dish);
        return dish;
    }
    public void deleteDish(long dishId) {
        dishes.removeIf(d -> d.getId() == dishId);
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

    public List<Dish> getDishesByCategory(DishCategory category) {
        List<Dish> toRet = new ArrayList<>();
        for (Dish dish : getDishes()) {
            if (dish.getCategory().ordinal() == category.ordinal())
                toRet.add(dish);
        }
        return toRet;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTotalChairs(Integer totalChairs) {
        this.totalChairs = totalChairs;
    }

    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }

}
