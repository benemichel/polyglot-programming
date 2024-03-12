import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class JavaAccessJsTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
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
