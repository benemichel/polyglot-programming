import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

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
    public void evaluateCppCodeWithArguments() throws Exception {

        String[] arguments = new String[] {"42"};

        Context context = Context.newBuilder().arguments("llvm", arguments).allowAllAccess(true).build();
        URL url = getClass().getResource("MainProcessPayment.so");
        File file = new File(url.getPath());
        
        Source source = Source.newBuilder("llvm", file).build();

        Value cpart = context.eval(source);
        Value result = cpart.execute();
      
        assertEquals(43, result.asInt());
    }

    
    @AfterEach
    public void tearDown() {
        context.close();
    }
}
