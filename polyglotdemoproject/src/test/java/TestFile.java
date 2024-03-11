import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class TestFile {

    @Test
    public void undefinedAndNullSemanticsAreLost() {
        Context context = Context.newBuilder().allowAllAccess(true).build();

        Value undefinedJs = context.eval("js", "let foo");
        Value nullJs = context.eval("js", "foo");

        assertEquals(undefinedJs, nullJs);
    }
}
