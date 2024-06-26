package com.awb.MyLibrary.models;



//Clasa pentru colectare utilizator si pentru a nu se creea o confuzie cu clasa numita user definita de Spring Security
//o denumim AppUser

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class AppUser {

    //Cheie Primara
    @Id
    //Generare incrementare automata
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Creare atribute (id, firsName, lastName, email. password, role)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Loan> loans = new HashSet<>();
    //Identificator unic al utilizatorului (email) - folosint la autentificare prin mail + pass
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String role;


    //Creare Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
