package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.*;

public class DishPatchForm {

    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String name;

    @NotNull
    @Positive
    private Integer price;

    @Size(min = 1, max = 200)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String description;

    @NotNull
    private Long imageId;

    @NotNull
    private Long categoryId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
