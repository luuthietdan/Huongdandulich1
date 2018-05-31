package com.example.lausecdan.huongdandulich.Model;

public class Khachsan {
    private String Name;
    private String Image;

    public Khachsan() {
    }

    public Khachsan(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
