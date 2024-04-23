package com.polyglot.demo.project.controller;

import java.io.File;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/payment")
public class PaymentController {
   
    ResourceLoader resourceLoader;

    public PaymentController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Operation(summary = "Process a payment")
    @GetMapping("/process")
    String process() {
        String[] arguments = new String[] {"421237"};

        try (Context context = Context.newBuilder("llvm").arguments("llvm", arguments).allowAllAccess(true).build();) {
            File file = resourceLoader.getResource( "classpath:/MainProcessPayment.so").getFile();
            Source source = Source.newBuilder("llvm", file).build();
    
            Value cpart = context.eval(source);
            Value result = cpart.execute();
            int resultInt = result.asInt();
          
            return String.valueOf(resultInt);  
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}