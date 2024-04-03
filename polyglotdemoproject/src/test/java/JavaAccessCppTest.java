import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class JavaAccessCppTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder("llvm").allowAllAccess(true).build();
    }

    @Test
    public void evaluateCppCode() throws Exception {

        URL url = getClass().getResource("MainProcessPayment.so");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("llvm", file).build();

        context.eval(source);
        Value cpart = context.getBindings("llvm").getMember("main");

        int valueInt = cpart.execute().asInt();

        assertEquals(1, valueInt);
    }

    
    @AfterEach
    public void tearDown() {
        context.close();
    }
}
