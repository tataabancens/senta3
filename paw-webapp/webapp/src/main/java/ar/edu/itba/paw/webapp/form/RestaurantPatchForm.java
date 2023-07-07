package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RestaurantPatchForm {
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z 0-9,.'-]+$")
    private String restaurantName;

    @Size(min = 8, max = 13)
    private String phone;

    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
    private String mail;

    @Size(min = 1, max = 4)
    private Integer totalChairs;

    @Size(min = 0, max = 2)
    @Pattern(regexp = "[1]?[0-9]|[2][0-4]")
    private Integer openHour;
    @Size(min = 0, max = 2)
    @Pattern(regexp = "[1]?[0-9]|[2][0-4]")
    private Integer closeHour;

    private int pointsForDiscount;

    public int getPointsForDiscount() {
        return pointsForDiscount;
    }

    public void setPointsForDiscount(int pointsForDiscount) {
        this.pointsForDiscount = pointsForDiscount;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getTotalChairs() {
        return totalChairs;
    }

    public void setTotalChairs(Integer totalChairs) {
        this.totalChairs = totalChairs;
    }

    public Integer getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    public Integer getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }
}
