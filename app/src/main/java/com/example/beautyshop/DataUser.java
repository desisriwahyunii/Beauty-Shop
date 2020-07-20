package com.example.beautyshop;

public class DataUser {
    String Id;
    String Username;
    String Email;
    String Password;

    public DataUser(String Id, String Username, String Email, String Password){
        this.Id = Id;
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
}

    public String getId() {
        return Id;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}