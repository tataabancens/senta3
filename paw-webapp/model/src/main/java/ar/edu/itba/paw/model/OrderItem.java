package ar.edu.itba.paw.model;

public class OrderItem {
    private long reservationId;
    private long dishId;
    private float unitPrice;
    private int quantity;
    private OrderItemStatus status;

    public OrderItem(long reservationId, long dishId, float unitPrice, int quantity /*, OrderItemStatus status */) {
        this.reservationId = reservationId;
        this.dishId = dishId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        //this.status = status;
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

    public void setStatus(OrderItemStatus status) {
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

    public OrderItemStatus getStatus() {
        return status;
    }
}
