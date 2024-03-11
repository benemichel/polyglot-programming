package com.polyglot.demo.project.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import com.polyglot.demo.project.classes.PromiseExecutor;
import com.polyglot.demo.project.interfaces.AbstractClassA;


import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;



@RestController
@RequestMapping("/java-c-demo")
public class JavaAccessCDemoController {

    private ResourceLoader resourceLoader;
    private String cPath = "classpath:";

    public JavaAccessCDemoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 
     * TODO: check!
     */
    @Operation(summary = "simple addition")
    @GetMapping("/addition")
    String rateProduct() {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {

            Source source = Source
            .newBuilder("llvm", resourceLoader.getResource(cPath + "/struct.so").getFile()
            ).build();


            Value value = context.eval(source);
            int valueInt = value.asInt();
            return String.valueOf(valueInt);
        } catch (Exception e) {
            return e.toString();

        }
    }
}