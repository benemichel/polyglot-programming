package com.polyglot.demo.project.controller;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.graalvm.polyglot.Context;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.entity.Product;
import com.polyglot.demo.project.enums.ProductTags;
import com.polyglot.demo.project.service.ImportService;
import com.polyglot.demo.project.service.RecommendationService;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/product")
public class ProductController {

    private RecommendationService recommendationService;
    private ImportService importService;

    public ProductController(RecommendationService recommendationService, ImportService importService) {
        this.recommendationService = recommendationService;
        this.importService = importService;
    }

   
    @Operation(summary = "Recommend a product")
    @GetMapping("/recommend")
    String recommendProduct() {

        List<ProductTags> shoeTags= Arrays.asList(ProductTags.FASHION, ProductTags.SALE, ProductTags.SUMMER);
        List<ProductTags> tvTags= Arrays.asList(ProductTags.SALE, ProductTags.ELECTRONICS);
        List<ProductTags> shirtTags= Arrays.asList(ProductTags.FASHION, ProductTags.SUMMER);
        List<ProductTags> deskTags= Arrays.asList( ProductTags.SALE);

        Product shoes = new Product("123456789", "shoes", shoeTags);
        Product tv = new Product("123456789", "tv", tvTags);
        Product shirt = new Product("123456789", "shirt", shirtTags);
        Product desk = new Product("123456789", "desk", deskTags);

    
        ArrayList<Product> products = new ArrayList<>();

        products.add(shoes);
        products.add(tv);
        products.add(shirt);
        products.add(desk);

        Product recommendedProduct = recommendationService.recommend(shoes, products);
        return recommendedProduct.getName();
    }

    @Operation(summary = "Find products in import file with Ruby")
    @GetMapping("/find-in-file-ruby")
    String findInImportFileRuby() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classloader.getResourceAsStream("products.txt");

            String fileContent = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            String result = importService.find_ean(fileContent);

            return result;
        } catch (Exception exception) {
            return (exception.toString());
        }

    }

    @Operation(summary = "Find products in import file with Java")
    @GetMapping("/find-in-file-java")
    String findInImportFileJava() {
        

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classloader.getResourceAsStream("products.txt");

            String fileContent = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            String eanPattern = "\\b\\d{13}\\b";
            Pattern pattern = Pattern.compile(eanPattern);
            Matcher matcher = pattern.matcher(fileContent);

            Boolean found = matcher.find();

            String result = found ? matcher.group(0) : "No EAN found";
           
            return result;
        } catch (Exception exception) {
            return (exception.toString());
        }
    }

    @Operation(summary = "Export a product as XML file")
    @GetMapping("/export")
    String export() throws Exception {
        List<ProductTags> tags= Arrays.asList( ProductTags.SALE);
        Product product = new Product("4006381333934", "Example Product", tags);

        try {
            String[] roots = new String[] { "src/main/groovy/" };
            GroovyScriptEngine gse = new GroovyScriptEngine(roots);
            Class<GroovyObject> productExportServiceClass = gse.loadScriptByName("ProductExportService.groovy");
            GroovyObject productExportService = productExportServiceClass.getDeclaredConstructor().newInstance();

            Object xml = productExportService.invokeMethod("createXml", product);

            return String.valueOf(xml);
        } catch (Exception e) {
            return e.toString();
        }

    }

}