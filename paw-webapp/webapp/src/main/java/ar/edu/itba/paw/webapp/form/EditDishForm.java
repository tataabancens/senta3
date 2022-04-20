package ar.edu.itba.paw.webapp.form;

public class EditDishForm {

    String dishName;

    String dishDesc;

    Double dishPrice;

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
}
