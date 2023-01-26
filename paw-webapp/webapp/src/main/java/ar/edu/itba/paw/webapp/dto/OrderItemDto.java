package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.OrderItem;
import ar.edu.itba.paw.model.Reservation;
import ar.edu.itba.paw.model.enums.OrderItemStatus;

import javax.ws.rs.core.UriInfo;


public class OrderItemDto {
    private long id;
    private long dishId;
    private long reservationId;
    private float unitPrice;
    private int quantity;
    private OrderItemStatus status;

    public static OrderItemDto fromOrderItem(final UriInfo uriInfo, OrderItem orderItem) {
        final OrderItemDto dto = new OrderItemDto();

        dto.id = orderItem.getId();
        dto.dishId = orderItem.getDish().getId();
        dto.reservationId = orderItem.getReservation().getId();
        dto.unitPrice = orderItem.getUnitPrice();
        dto.unitPrice = orderItem.getUnitPrice();
        dto.status = orderItem.getStatus();

        return dto;
    }

    public OrderItemDto() {
        // para Jersey
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }
}