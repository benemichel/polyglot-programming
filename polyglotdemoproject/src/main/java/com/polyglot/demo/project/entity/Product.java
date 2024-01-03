package com.polyglot.demo.project.entity;

public class Product {
    private int rating;
    private String ean13;
    private String name;
    private float netPrice;

     public Product(String ean, String name) {
        this.name = name;
        this.ean13 = ean;
        this.rating = 0;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }
}
