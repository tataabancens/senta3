package ar.edu.itba.paw.model;


import ar.edu.itba.paw.model.enums.DishCategory;

public class Dish {
    private long id;
    private long restaurantId;
    private String dishName;
    private int price;
    private String dishDescription;
    private long imageId;
    private DishCategory category;

    public Dish(long id, long restaurantId, String dishName,
                int price, String dishDescription,
                long imageId, DishCategory category) {
        super();
        this.id = id;
        this.restaurantId = restaurantId;
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
        return restaurantId;
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
        this.restaurantId = restaurantId;
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

}
