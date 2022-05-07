package ar.edu.itba.paw.model;

public class User {
    private long id;
    private String username;
    private String password;
    private String role;

    public User(long id, String username, String password, String role) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}
