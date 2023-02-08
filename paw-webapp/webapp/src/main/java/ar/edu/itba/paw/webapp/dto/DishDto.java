package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Dish;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class DishDto {
    private long id;
    private String name;
    private URI self;
    private URI restaurant;
    private URI image;
    private URI category;
    private String description;
    private int price;


    public DishDto() {
        // for jersey
    }

    public static DishDto fromDish (final UriInfo uriInfo, Dish dish){
        DishDto dishDto = new DishDto();

        dishDto.id = dish.getId();
        dishDto.name = dish.getDishName();
        dishDto.description = dish.getDishDescription();
        dishDto.price = dish.getPrice();

        final UriBuilder dishesUriBuilder = uriInfo.getBaseUriBuilder()
                .path("restaurants")
                .path(String.valueOf(dish.getRestaurant().getId()))
                .path("dishes").path(String.valueOf(dish.getId()));
        dishDto.self = dishesUriBuilder.build();

        UriBuilder dishCategoryBuilder = uriInfo.getBaseUriBuilder()
                .path("restaurants").path(String.valueOf(dish.getRestaurantId()))
                .path("dishCategories")
                .path(String.valueOf(dish.getCategory().getId()));
        dishDto.category = dishCategoryBuilder.build();

        UriBuilder dishImageBuilder = uriInfo.getBaseUriBuilder()
                .path("resources").path("images")
                .path(String.valueOf(dish.getImageId()));
        dishDto.image = dishImageBuilder.build();

        UriBuilder restaurantBuilder = uriInfo.getBaseUriBuilder()
                .path("restaurants")
                .path(String.valueOf(dish.getRestaurant().getId()));
        dishDto.restaurant = restaurantBuilder.build();
        return dishDto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(URI restaurant) {
        this.restaurant = restaurant;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getCategory() {
        return category;
    }

    public void setCategory(URI category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
