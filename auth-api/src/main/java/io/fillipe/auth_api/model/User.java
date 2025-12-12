package io.fillipe.auth_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    //Campos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    //Construtor padrão
    public User() {
    }

    // Construtor completo
    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    //Getter e Setters
    public Long getId() {
        return id;
    }

    /*
    public void setId(Long id) {
        this.id = id;
    }
     */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
