package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.customValidator.TableNumberConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TableNumberForm {

    @TableNumberConstraint
    @Pattern(regexp = "[1-9][0-9]+|[1-9]")
    @Size(max = 3)
    String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
