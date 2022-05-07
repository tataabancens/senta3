package ar.edu.itba.paw.webapp.form;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditNameForm {

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z ,.'-]+$")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
