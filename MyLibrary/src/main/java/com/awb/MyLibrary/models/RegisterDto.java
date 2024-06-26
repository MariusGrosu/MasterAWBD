package com.awb.MyLibrary.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

//Creare model pentru a permite citirea datelor trimise din formularul de inregistrare
public class RegisterDto {

    @NotEmpty // Este necesar - obligatoriu sa avem firstName
    private String firstName;

    @NotEmpty // Este necesar - obligatoriu sa avem lastName
    private String lastName;

    @NotEmpty // Este necesar - obligatoriu sa avem email
    @Email //Sa aiba formatul unei adrese de mail
    private String email;


    @Size(min = 5, message = "Minimum password length is 5 characters")
    private String password;
    private String confirmPassword;
    private String role;


    //Creare Getters and Setters


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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
