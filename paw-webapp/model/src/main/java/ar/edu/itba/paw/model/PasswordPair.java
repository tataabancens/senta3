package ar.edu.itba.paw.model;

import com.sun.istack.internal.NotNull;

public class PasswordPair {
    @NotNull
    private String password;
    @NotNull
    private String checkPassword;

    public PasswordPair(String password, String checkPassword) {
        this.password = password;
        this.checkPassword = checkPassword;
    }
    public PasswordPair() {

    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }
}
