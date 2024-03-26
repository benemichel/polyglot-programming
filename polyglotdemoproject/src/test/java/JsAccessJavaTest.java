import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.BiConsumer;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class JsAccessJavaTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder("js").allowAllAccess(true).build();
    }

    @Test
    public void accessJavaObjectAndSetProperty() throws Exception {
        Triangle triangle = new Triangle(5, 4);
        context.getBindings("js").putMember("triangle", triangle);
        Value result = context.eval("js", "triangle.g = 20; triangle.getArea()");
        double resultDouble = result.asDouble();
        assertEquals(40.0, resultDouble, 0.0);
    }

    @Test
    public void loopOverJavaObjectProperties() throws Exception {
        TestOutput out = new TestOutput();
        Context context = Context.newBuilder().out(out).allowHostAccess(HostAccess.ALL).build();

        URL url = getClass().getResource("loopObject.js");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("js", file).build();

        Triangle triangle = new Triangle(5, 4);
        context.getBindings("js").putMember("triangle", triangle);
        context.eval(source);
        assertEquals("g: 5\n" +
                "h: 4\n" +
                "getArea: function () { [native code] }\n",
                out.toString());
    }

    @Test
    public void excutePromiseWithJavaExecutor() throws Exception {
        TestOutput out = new TestOutput();
        Context context = Context.newBuilder().out(out).allowHostAccess(HostAccess.ALL).build();

        URL url = getClass().getResource("executePromise.js");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("js", file).build();

        PromiseExecutor javaExecutor = new PromiseExecutor();
        context.getBindings("js").putMember("javaExecutor", javaExecutor);
        context.eval(source);

        assertEquals("Resolved Promise Value\n", out.toString());
    }

    public class Triangle {
        public double g;
        public double h;

        public Triangle(double g, double h) {
            this.g = g;
            this.h = h;
        }

        public double getArea() {
            return 0.5 * g * h;
        }
    }

    static class TestOutput extends ByteArrayOutputStream {

        void write(String text) {
            byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
            write(bytes, 0, bytes.length);
        }

        @Override
        public synchronized String toString() {
            return new String(toByteArray(), StandardCharsets.UTF_8).replace("\r\n", "\n");
        }
    }

    public class PromiseExecutor implements BiConsumer<Value, Value> {

        @Override
        public void accept(Value resolve, Value reject) {
            resolve.execute("Resolved Promise Value");
        }

    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
