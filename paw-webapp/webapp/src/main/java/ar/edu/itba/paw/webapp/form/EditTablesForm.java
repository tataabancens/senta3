package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditTablesForm {

    @Size(min = 1, max = 4)
    @Pattern(regexp = "[0-9]+")
    String tableQty;

    @Size(min = 0, max = 2)
    @Pattern(regexp = "[1]?[0-9]|[2][0-4]")
    String openHour;
    @Size(min = 0, max = 2)
    @Pattern(regexp = "[1]?[0-9]|[2][0-4]")
    String closeHour;

    public String getTableQty() {
        return tableQty;
    }

    public void setTableQty(String tableQty) {
        this.tableQty = tableQty;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }
}
