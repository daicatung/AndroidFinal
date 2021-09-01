package com.example.androidfinal.model;

import java.io.Serializable;

public class User implements Serializable {
    public String nameUser;
    public String imgUser;
    public String dateUser;
    public String emailUser;
    public String sexUser;

    public User(String nameUser, String imgUser, String dateUser, String emailUser, String sexUser) {
        this.nameUser = nameUser;
        this.imgUser = imgUser;
        this.dateUser = dateUser;
        this.emailUser = emailUser;
        this.sexUser = sexUser;
    }
}
