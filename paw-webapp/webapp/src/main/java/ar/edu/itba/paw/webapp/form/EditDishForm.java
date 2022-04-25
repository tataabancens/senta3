package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public class EditDishForm {

    private String dishName;

    private String dishDesc;

    private Double dishPrice;

    private MultipartFile photo;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDesc() {
        return dishDesc;
    }

    public void setDishDesc(String dishDesc) {
        this.dishDesc = dishDesc;
    }

    public Double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}
