package com.example.androidfinal.model;

import java.io.Serializable;

public class Cast implements Serializable {
    public String nameCast;
    public String imgCast;

    public Cast(String nameCast, String imgCast) {
        this.nameCast = nameCast;
        this.imgCast = imgCast;
    }

    public String getNameCast() {
        return nameCast;
    }

    public void setNameCast(String nameCast) {
        this.nameCast = nameCast;
    }

    public String getImgCast() {
        return imgCast;
    }

    public void setImgCast(String imgCast) {
        this.imgCast = imgCast;
    }
}
