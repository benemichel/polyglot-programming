package com.polyglot.demo.project.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.interfaces.InterfaceA;
import com.polyglot.demo.project.interfaces.InterfaceB;
import com.polyglot.demo.project.interfaces.LinearRegression;

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
public class PythonAccessJavaDemoController {

    private ResourceLoader resourceLoader;
    private String pythonPath = "classpath:";

    public PythonAccessJavaDemoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "access Java standard class and call method")
    @GetMapping("/access-java-standard-class")
    String accessJavaStandardClassAndCallMethod() {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/JavaMath.py").getFile()
            ).build();
            Value result = context.eval(source);
            int resultInt = result.asInt();

            return String.valueOf(resultInt);
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "access Java custom class")
    @GetMapping("/access-java-custom-class")
    String accessJavaCustomClass() {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).option("python.ForceImportSite", "true").build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/CalculateRectangle.py").getFile()
            ).build();
            Value result = context.eval(source);
            float resultFloat = result.asFloat();

            return String.valueOf(resultFloat);
        } catch (Exception e) {
            return e.toString();
        }
    }

     /**
     * checked: 04.03.2024
     */
    @Operation(summary = "call js")
    @GetMapping("/call-js")
    String callJavaScript() {
        try (Context context = Context.newBuilder("python", "js").allowAllAccess(true).build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/CallJavaScript.py").getFile()
            ).build();
            Value result = context.eval(source);
            int resultInt = result.asInt();

            return String.valueOf(resultInt);
        } catch (Exception e) {
            return e.toString();
        }
    }
}