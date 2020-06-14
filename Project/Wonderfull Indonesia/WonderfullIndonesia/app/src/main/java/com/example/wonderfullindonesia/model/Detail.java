package com.example.wonderfullindonesia.model;

public class Detail {
    int id;

    public Detail() {
    }

    public Detail(int id, int idcode, int code, String title, String images, String desc) {
        this.id = id;
        this.idcode = idcode;
        this.code = code;
        this.title = title;
        this.image = images;
        this.desc = desc;
    }

    int idcode;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    int code;
    String title, image, desc;
}
