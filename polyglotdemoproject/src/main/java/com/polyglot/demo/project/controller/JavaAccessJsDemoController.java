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
@RequestMapping("/java-js-demo")
public class JavaAccessJsDemoController {

    private ResourceLoader resourceLoader;
    private String jsPath = "classpath:";

    public JavaAccessJsDemoController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Operation(summary = "simple addition")
    @GetMapping("/addition")
    String rateProduct() {
        try (Context context = Context.create()) {
            Value value = context.eval("js", "4 + 6");
            int valueInt = value.asInt();
            return String.valueOf(valueInt);
        } catch (Exception e) {
            return e.toString();

        }
    }

    @Operation(summary = "use simple function")
    @GetMapping("/simple-function")
    String useFunction() {
        try (Context context = Context.create()) {
            Value value = context.eval("js", "((x,y) => x + y)");
            Value result = value.execute(4, 6);
            int resultInt = result.asInt();
            return String.valueOf(resultInt);
        } catch (Exception e) {
            return e.toString();

        }
    }

    @Operation(summary = "use two function")
    @GetMapping("/two-functions")
    String useTwoFunction() {
        try (Context context = Context.create()) {
            Source source = Source
            .newBuilder("js", resourceLoader.getResource(jsPath + "/twoFunctions.js").getFile()
            ).build();

            context.eval(source);

            Value add = context.getBindings("js").getMember("add");
            Value sub = context.getBindings("js").getMember("sub");

            Value addResult = add.execute(4, 6);
            Value subResult = sub.execute(4, 6);

            int addResultInt = addResult.asInt();
            int subResultInt = subResult.asInt();
            return "add: " + String.valueOf(addResultInt) + " sub: " + String.valueOf(subResultInt) ;
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**
     * checked: 04.03.2024
     */
    @Operation(summary = "throws PolyglotException")
    @GetMapping("/throw-polyglot-exception")
    String throwPolyglotException() {
        try (Context context = Context.create()) {
            context.eval("js", "fooObject.value");
            return "";
        } catch (Exception e) {
            return e.toString();
        }
    }


    @Operation(summary = "test debugging")
    @GetMapping("/test-debugging")
    String testDebugging() {
        try (Context context = Context.create()) {
            Source source = Source
            .newBuilder("js", resourceLoader.getResource(jsPath + "/testDebugging.js").getFile()
            ).build();

            Value result = context.eval(source);
            int resultInt = result.asInt();

            return String.valueOf(resultInt);
        } catch (Exception e) {
            return e.toString();
        }
    }


    /**
     * checked: 05.03.2024
     */
    @Operation(summary = "execute promise")
    @GetMapping("/execute-promise")
    String promiseExecutor() {
        try (Context context = Context.newBuilder().allowAllAccess(true).build()) {
          
            Value promise = context.eval("js", "Promise.resolve(42);");
            Consumer<Object> then = (val) -> System.out.println("Resolved: " + val);
            promise.invokeMember("then", then);

            return "see terminal output";
        } catch (Exception e) {
            return e.toString();
        }
    }

        /**
     * checked: 05.03.2024
     */
    @Operation(summary = "multiple-contexts-multiple-threads")
    @GetMapping("/multiple-contexts-multiple-threads")
    String multipleContextsMultipleThreads() {
        try (Context context1 = Context.newBuilder().allowAllAccess(true).build();
             Context context2 = Context.newBuilder().allowAllAccess(true).build() ) {
          
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    context1.eval("js", "console.log('context 1')");
                }
            });

            thread.start();

            for (int i = 0; i < 10; i++) {
                context2.eval("js", "console.log('context 2')");
            }
           
            thread.join();
            
            return "see terminal output";
        } catch (Exception e) {
            return e.toString();
        }
    }
}