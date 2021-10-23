package com.am2.eshopapp.ui.home.entities;

public class ProductEntity {

    private String uid;
    private String name;
    private Double price;
    private int stock;
    private int category;
    private int state;
    private String description;

    public ProductEntity(String uid, String name, Double price, int stock, int category, int state, String description) {
        this.uid = uid;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.state = state;
        this.description = description;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
