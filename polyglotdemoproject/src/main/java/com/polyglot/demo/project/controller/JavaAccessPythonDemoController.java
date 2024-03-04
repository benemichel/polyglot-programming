package com.polyglot.demo.project.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polyglot.demo.project.interfaces.InterfaceA;
import com.polyglot.demo.project.interfaces.InterfaceB;
import com.polyglot.demo.project.interfaces.LinearRegression;

import io.swagger.v3.oas.annotations.Operation;

import java.util.function.Function;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;

/**
 * Demonstrates how to access foreign Python code from Java
 */
@RestController
@RequestMapping("/java-python-demo")
public class JavaAccessPythonDemoController {

    private ResourceLoader resourceLoader;
    private String pythonPath = "classpath:";

    public JavaAccessPythonDemoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "cast python class to Java interface and call static method (ClassB)")
    @GetMapping("/cast-and-call-static-method")
    String castPythonObjectToJavaInterfaceAndCallStaticMethod() {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/ClassB.py").getFile()
            ).build();
            context.eval(source);

            Value classB = context.getBindings("python").getMember("ClassB");
            InterfaceB interfaceB = classB.as(InterfaceB.class);
            int result = interfaceB.add(4, 15);

            return String.valueOf(result);
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "instantiate python class and cast to Java interface and call method (ClassA)")
    @GetMapping("/cast-to-java-interface")
    String castPythonObjectToJavaInterface() {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/ClassA.py").getFile()
            ).build();
            context.eval(source);

            Value classA = context.getBindings("python").getMember("ClassA");
            InterfaceA interfaceAImplementation = classA.newInstance().as(InterfaceA.class);
            int result = interfaceAImplementation.sum();

            return String.valueOf(result);
        } catch (Exception e) {
            return e.toString();
        }
    }


    @Operation(summary = "implement service in python (linear regression)")
    @GetMapping("/linear-regression-interface")
    String linearRegressionInterface() {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {
            Source source = Source
            .newBuilder("python", resourceLoader.getResource(pythonPath + "/LinearRegressionImpl.py").getFile()
            ).build();

            context.eval(source);

            Value LinearRegressionImpl = context.getBindings("python").getMember("LinearRegressionImpl").newInstance();
            LinearRegression linearRegressionImpl = LinearRegressionImpl.as(LinearRegression.class);
            int result = linearRegressionImpl.compute();
    
            return String.valueOf(result);
        } catch (Exception e) {
            return e.toString();
        }
    }
}