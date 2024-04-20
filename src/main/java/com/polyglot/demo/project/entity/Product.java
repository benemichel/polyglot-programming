package com.polyglot.demo.project.entity;

import com.polyglot.demo.project.enums.ProductCategories;

public class Product {
    private int rating;
    private String ean13;
    private String name;
    private float netPrice;
    private ProductCategories category;
    private int color;

     public Product(String ean, String name, ProductCategories category) {
        this.name = name;
        this.ean13 = ean;
        this.rating = 0;
        this.category = category;
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

    public ProductCategories getCategory() {
        return category;
    }

    public int getColor() {
        return color;
    }
}
