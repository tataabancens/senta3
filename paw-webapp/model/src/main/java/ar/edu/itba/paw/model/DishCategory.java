package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class DishCategory {

    @Id
    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurantid", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dish = new ArrayList<>();

    public String getName() {
        return name;
    }

    DishCategory() {
        // Just or hibernate
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dish;
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
