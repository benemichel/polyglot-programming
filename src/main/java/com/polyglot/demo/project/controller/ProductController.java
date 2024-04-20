package com.polyglot.demo.project.controller;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.graalvm.polyglot.Context;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.entity.Product;
import com.polyglot.demo.project.enums.ProductCategories;
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

    @Operation(summary = "Find products in import file")
    @GetMapping("/find-in-file")
    String findInImportFile() {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {

            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classloader.getResourceAsStream("products.txt");

            String fileContent = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            String result = importService.find_ean(fileContent);

            return result;
        } catch (Exception exception) {
            return (exception.toString());
        }

    }

    @Operation(summary = "Export a product as XML file")
    @GetMapping("/export")
    String export() throws Exception {
        Product product = new Product("4006381333934", "Example Product", ProductCategories.CLOTHES);

        try {
            String[] roots = new String[] { "polyglotdemoproject/src/main/groovy" };
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