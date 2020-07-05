package com.sbo.maps;

public class RegistrationData {

    public RegistrationData(String password, String email, String first_name, String surname) {
        this.password = password;
        this.email = email;
        this.first_name = first_name;
        this.surname = surname;
    }
    public RegistrationData(){

    }

    String password, email, first_name, surname;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
