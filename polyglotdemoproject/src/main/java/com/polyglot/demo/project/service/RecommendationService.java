package com.polyglot.demo.project.service;

import java.util.ArrayList;

import com.polyglot.demo.project.entity.Product;

public interface RecommendationService {
    
     public Product recommend(Product product, ArrayList<Product> products);

    public void pythonConstructor();
    
}
