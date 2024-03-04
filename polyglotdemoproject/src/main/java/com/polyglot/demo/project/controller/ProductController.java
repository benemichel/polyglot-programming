package com.polyglot.demo.project.controller;

import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;

import org.graalvm.polyglot.Context;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.entity.Product;
import com.polyglot.demo.project.enums.ProductCategories;
import com.polyglot.demo.project.service.RecommendationService;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RecommendationService recommendationService;

    public ProductController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * checked: 04.03.2024, TODO: use numpy!
     */
    @Operation(summary = "Recommend a product")
    @GetMapping("/recommend")
    String recommendProduct() {

        Product shoes = new Product("123456789", "shoes", ProductCategories.CLOTHES);
        Product tv = new Product("123456789", "tv", ProductCategories.ELECTRONICS);
        Product shirt = new Product("123456789", "shirt", ProductCategories.CLOTHES);
        Product desk = new Product("123456789", "desk", ProductCategories.FURNITURE);
        ArrayList<Product> products = new ArrayList<>();

        products.add(shoes);
        products.add(tv);
        products.add(shirt);
        products.add(desk);

        Product recommendedProduct = recommendationService.recommend(shoes, products);
        return recommendedProduct.getName();
    }


    @Operation(summary = "Rate a product")
    @GetMapping("/rate")
    String rateProduct() {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
            Value value = context.eval("python", "4 + 6");
            int valueInt = value.asInt();
            return String.valueOf(valueInt);
        } catch (Exception e) {
            return e.toString();

        }
    }

    @Operation(summary = "Export a product as XML file")
    @GetMapping("/export")
    String export()
            throws IOException, ResourceException, ScriptException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        Product product = new Product("4006381333934", "Example Product", ProductCategories.CLOTHES);

        try {
         
        
            String[] roots = new String[] { "src/main" };
            GroovyScriptEngine gse = new GroovyScriptEngine(roots);
            Class<GroovyObject> productExportServiceClass = gse.loadScriptByName("groovy/ProductExportService.groovy");
            GroovyObject productExportService = productExportServiceClass.getDeclaredConstructor().newInstance();

            Object xml = productExportService.invokeMethod("createXml", product);

            return String.valueOf(xml);
        } catch (Exception e) {
            return e.toString();
        }

    }

}