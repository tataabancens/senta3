package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.model.DishCategory;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
;

public class DishCategoryDto {
    private String name;
    private URI dishes;

    public DishCategoryDto() {
        // for jersey
    }

    public static DishCategoryDto fromDishCategory(UriInfo uriInfo, DishCategory dishCategory){
        DishCategoryDto dishCategoryDto = new DishCategoryDto();

        dishCategoryDto.name = dishCategory.getName();
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .replacePath("restaurants").path(String.valueOf(dishCategory.getRestaurant().getId()))
                .path("dishes").queryParam("category", dishCategory.getName());
        dishCategoryDto.dishes = uriBuilder.build();

        return dishCategoryDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getDishes() {
        return dishes;
    }

    public void setDishes(URI dishes) {
        this.dishes = dishes;
    }
}
