package com.polyglot.demo.project;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class JavaAccessJsTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }


    @Test
    public void evaluateJsCode() {
        Value value = context.eval("js", "4 + 6");
        int valueInt = value.asInt();
        assertEquals(10, valueInt);
    }

    @Test
    public void executeJsFunction() {
        Value value = context.eval("js", "((x,y) => x + y)");
            Value result = value.execute(4, 6);
            int resultInt = result.asInt();
            assertEquals(10, resultInt);
    }

    @Test
    public void accessTwoJsFunctionsViaBindings() throws Exception {

         URL url = getClass().getResource("twoFunctions.js");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("js", file).build();

        context.eval(source);

        Value add = context.getBindings("js").getMember("add");
        Value sub = context.getBindings("js").getMember("sub");

        Value addResult = add.execute(6, 4);
        Value subResult = sub.execute(6, 4);

        assertEquals(10, addResult.asInt());
        assertEquals(2, subResult.asInt());
    }

    @Test
    public void throwsPolyglotException() {
        assertThrows(PolyglotException.class, () -> {
            context.eval("js", "fooObject.value");
        });
    }

    @Test
    public void exeutePromiseWithThenFromJava() {
        TestOutput out = new TestOutput();
        Context context = Context.newBuilder().out(out).allowHostAccess(HostAccess.ALL).build();

        Value promise = context.eval("js", "Promise.resolve(42);");
        Consumer<Object> then = (val) -> out.write("Resolved: " + val);
        promise.invokeMember("then", then);

        assertEquals("Resolved: 42", out.toString());
    }

    @Test
    public void evaluateMultipleContextsInMultipleThreads() {
        Context context1 = Context.newBuilder().allowAllAccess(true).build();
             Context context2 = Context.newBuilder().allowAllAccess(true).build();
          
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    Value value = context1.eval("js", "'context 1'");
                    String valueString = value.asString();
                    assertEquals("context 1", valueString);
                }
            });

            thread.start();

            for (int i = 0; i < 10; i++) {
                Value value = context2.eval("js", "'context 2'");
                String valueString = value.asString();
                assertEquals("context 2", valueString);
            }
           
            assertDoesNotThrow(() -> {
                // Waits for this thread to terminate.
                thread.join(); 
            });
            
    }



    @Test
    public void undefinedAndNullSemanticsAreLost() {
        Value undefinedJs = context.eval("js", "let foo; foo");
        Value nullJs = context.eval("js", "foo");

        assertEquals(undefinedJs, nullJs);
        assertTrue(undefinedJs.isNull());
        assertTrue(nullJs.isNull());
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }

    public static class TestOutput extends ByteArrayOutputStream {

        void write(String text) {
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            write(bytes, 0, bytes.length);
        }

        @Override
        public synchronized String toString() {
            return new String(toByteArray(), StandardCharsets.UTF_8).replace("\r\n", "\n");
        }
    }
}
