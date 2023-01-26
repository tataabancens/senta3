//package ar.edu.itba.paw.webapp.form;
//
//import ar.edu.itba.paw.model.DishCategory;
//
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//
//public class EditDishForm {
//
//    @Size(min = 1, max = 100)
//    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
//    private String dishName;
//
//    @Size(min = 1, max = 200)
//    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
//    private String dishDesc;
//
//    @Size(min = 1, max = 10)
//    @Pattern(regexp = "[1-9][0-9]+")
//    private String dishPrice;
//
//    private String category;
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getDishName() {
//        return dishName;
//    }
//
//    public void setDishName(String dishName) {
//        this.dishName = dishName;
//    }
//
//    public String getDishDesc() {
//        return dishDesc;
//    }
//
//    public void setDishDesc(String dishDesc) {
//        this.dishDesc = dishDesc;
//    }
//
//    public String getDishPrice() {
//        return dishPrice;
//    }
//
//    public void setDishPrice(String dishPrice) {
//        this.dishPrice = dishPrice;
//    }
//}
