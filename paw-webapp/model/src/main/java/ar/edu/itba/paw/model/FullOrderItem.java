package ar.edu.itba.paw.model;

import java.util.Objects;

public class FullOrderItem {
    
    private long reservationId;
    private long dishId;
    private float unitPrice;
    private int quantity;
    private int status;
    private String dishName;

    public FullOrderItem(long reservationId, long dishId, float unitPrice, int quantity , int status, String dishName) {
        this.reservationId = reservationId;
        this.dishId = dishId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.status = status;
        this.dishName = dishName;
    }

    public void setReservationId(long reservationId) {
            this.reservationId = reservationId;
    }
    public void setDishId(long dishId) {
            this.dishId = dishId;
    }
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getReservationId() {
        return reservationId;
    }

    public long getDishId() {
        return dishId;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
        
    public int getStatus() {
        return status;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
