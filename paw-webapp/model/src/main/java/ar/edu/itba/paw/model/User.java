package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq") //dijo "vamos a dejar el mismo nombre que está en la bdd postgres
    @SequenceGenerator(allocationSize = 1, sequenceName = "users_userid_seq", name = "users_userid_seq")
    @Column(name = "userid") // asume nombre de columna 'id', entonces le cambiamos el nombre
    private Long id;

    @Column(length = 100, unique = true) //si lo necesitara le podes poner nullable = false para un notnull
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String role;

    /* default */ User() {
        //just for hibernate
    }

    public User(String username, String password, String role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Deprecated
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
