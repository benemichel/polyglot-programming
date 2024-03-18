import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

import com.oracle.graal.vector.nodes.simd.v;

import io.swagger.v3.oas.annotations.Operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.graalvm.polyglot.Context;
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
}
