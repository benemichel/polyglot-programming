package com.polyglot.demo.project.classes;

import java.util.function.BiConsumer;

import org.graalvm.polyglot.Value;


public class PromiseExecutor implements BiConsumer<Value, Value> {

    @Override
    public void accept(Value resolve, Value reject) {
        resolve.execute("Resolved Promise Value");
    }

}
