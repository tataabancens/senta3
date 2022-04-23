package ar.edu.itba.paw.model;

public class Tables {
    private long tableId;
    private long restaurantId;
    private int hour;

    public Tables(long tableId, long restaurantId, int hour) {
        super();
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.hour = hour;
    }

    public long getId() {
        return tableId;
    }

    public void setId(long tableId) {
        this.tableId = tableId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
