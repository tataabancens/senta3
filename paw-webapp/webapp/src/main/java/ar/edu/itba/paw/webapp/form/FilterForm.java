package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.enums.ReservationStatus;
import org.springframework.web.bind.annotation.RequestParam;

public class FilterForm {

    private String filterStatus;
    private String direction;
    private String orderBy;

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
