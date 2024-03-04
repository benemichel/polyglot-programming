package com.polyglot.demo.project.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.classes.Triangle;

import io.swagger.v3.oas.annotations.Operation;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;


/**
 * Demonstrates how to access Java host objects in Python
 */
@RestController
@RequestMapping("/python-java-demo")
public class JavaScriptAccessJavaDemoController {

    private ResourceLoader resourceLoader;
    private String pythonPath = "classpath:";

    public JavaScriptAccessJavaDemoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "access object")
    @GetMapping("/access-java-object")
    String accessJavaObject() {
        try (Context context = Context.newBuilder("js").allowAllAccess(true).build()) {
        
            Triangle triangle = new Triangle(5, 4);
            context.getBindings("js").putMember("triangle", triangle);
            Value result = context.eval("js", "triangle.g = 20; triangle.getArea()");
            double resultDouble = result.asDouble();

            return String.valueOf(resultDouble);
        } catch (Exception e) {
            return e.toString();
        }
    }
}