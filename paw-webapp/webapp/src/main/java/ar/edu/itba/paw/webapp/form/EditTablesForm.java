package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditTablesForm {

    int tableQty;

    @Size(max = 23)
    int openHour;
    @Size(max=23)
    int closeHour;

    public int getTableQty() {
        return tableQty;
    }

    public void setTableQty(int tableQty) {
        this.tableQty = tableQty;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(int closeHour) {
        this.closeHour = closeHour;
    }
}
