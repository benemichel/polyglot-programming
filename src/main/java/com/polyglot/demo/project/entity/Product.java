package com.polyglot.demo.project.entity;

import java.util.List;

import com.polyglot.demo.project.enums.ProductTags;

public class Product {
    private int rating;
    private String ean13;
    private String name;
    private List<ProductTags> tags;
    private int color;

     public Product(String ean, String name, List<ProductTags> tags) {
        this.name = name;
        this.ean13 = ean;
        this.rating = 0;
        this.tags = tags;
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

    public int getColor() {
        return color;
    }

    public List<ProductTags> getTags() {
        return tags;
    }
}
