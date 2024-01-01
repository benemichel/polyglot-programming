package com.polyglot.demo.project.controller;

import org.graalvm.polyglot.Value;


import org.graalvm.polyglot.Context;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Operation(summary="Rate a product")
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

}