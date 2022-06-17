package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class DishCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishcategory_id_seq")
    @SequenceGenerator(sequenceName = "dishcategory_id_seq", name = "dishcategory_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> dish = new ArrayList<>();

    public String getName() {
        return name;
    }

    DishCategory() {
        // Just or hibernate
    }

    public DishCategory(Restaurant restaurant, String categoryName) {
        this.restaurant = restaurant;
        this.name = categoryName;
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

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Dish> getDish() {
        return dish;
    }

    public void setDish(List<Dish> dish) {
        this.dish = dish;
    }
}
