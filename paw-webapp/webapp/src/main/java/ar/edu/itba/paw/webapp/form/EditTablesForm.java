package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.OpenCloseHoursConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@OpenCloseHoursConstraint.List({
        @OpenCloseHoursConstraint(
                openHour = "openHour",
                closeHour = "closeHour"
        )
        })
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

    public String getErrorMessage() {
        return "No puede abrir antes de cerrar!";
    }
    public EditTablesForm getThis() {
        return this;
    }

}
