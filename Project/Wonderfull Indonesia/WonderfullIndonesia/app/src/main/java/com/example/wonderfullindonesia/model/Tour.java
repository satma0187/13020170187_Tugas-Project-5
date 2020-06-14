package com.example.wonderfullindonesia.model;

public class Tour {
    int id, idcode;
    String images, desc;

    public Tour() {
    }

    public Tour(int id, int idcode, String images, String desc) {
        this.id = id;
        this.idcode = idcode;
        this.images = images;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcode() {
        return idcode;
    }

    public void setIdcode(int idcode) {
        this.idcode = idcode;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
