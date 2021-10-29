package com.am2.eshopapp.Entities;

import com.google.firebase.firestore.DocumentId;

public class ProductEntity {
    @DocumentId
    private String id;
    private String name;
    private String price;
    private String stock;
    private String description;

    public ProductEntity(){}

    public ProductEntity(String name, String price, String stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}