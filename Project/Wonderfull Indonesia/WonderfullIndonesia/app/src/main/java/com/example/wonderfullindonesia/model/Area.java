package com.example.wonderfullindonesia.model;

public class Area {
    int id;
    String area;

    public Area() {
    }

    public Area(int id, String area) {
        this.id = id;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
