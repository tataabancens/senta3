package ar.edu.itba.paw.model;


public class Dish {
    private long id;
    private long restaurantId;
    private String dishName;
    private int price;

    public Dish(long id, long restaurantId, String dishName, int price) {
        super();
        this.id = id;
        this.restaurantId = restaurantId;
        this.dishName = dishName;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getPrice() {
        return price;
    }
}
