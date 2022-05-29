package ar.edu.itba.paw.model;


import ar.edu.itba.paw.model.enums.DishCategory;

import javax.persistence.*;

@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_dishid_seq")
    @SequenceGenerator(sequenceName = "dish_dishid_seq", name = "dish_dishid_seq", allocationSize = 1)
    @Column(name = "dishid")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurantid", nullable = false)
    private Restaurant restaurant;

    @Deprecated
    //private long restaurantId;

    @Column(length = 100, nullable = false)
    private String dishName;


    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 200)
    private String dishDescription;

    @Column(nullable = false)
    private long imageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private DishCategory category;

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    @Deprecated
    public Dish(long id, long restaurantId, String dishName,
                int price, String dishDescription,
                long imageId, DishCategory category) {
        super();
        this.id = id;
        //this.restaurantId = restaurantId;
        this.dishName = dishName;
        this.price = price;
        this.dishDescription = dishDescription;
        this.imageId = imageId;
        this.category = category;
    }

    /* default */ Dish() {
        // Just for hibernate
    }

    public Dish(Restaurant restaurant, String dishName,
                int price, String dishDescription,
                long imageId, DishCategory category) {
        super();
        this.restaurant = restaurant;
        this.dishName = dishName;
        this.price = price;
        this.dishDescription = dishDescription;
        this.imageId = imageId;
        this.category = category;
    }


    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public long getRestaurantId() {
        return restaurant.getId();
    }

    public String getDishName() {
        return dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurant.setId(restaurantId);
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public DishCategory getCategory() {
        return category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
