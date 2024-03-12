import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.classes.JavaArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class PassProxiesTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }

    @Test
    public void passArrayProxyToJsAndAccessTest() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);

        JavaArray javaArray = new JavaArray(list);
        context.getBindings("js").putMember("javaArray", javaArray);
        Value result = context.eval("js", "javaArray[1] + javaArray.length");
        int resultInt = result.asInt();

        assertEquals(1 + 3, resultInt);
    }

    @Test
    public void passArrayProxyToJsAndSetTest() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);

        JavaArray javaArray = new JavaArray(list);
        context.getBindings("js").putMember("javaArray", javaArray);
        PolyglotException exception = assertThrows(PolyglotException.class, () -> {
            context.eval("js", "javaArray.push(3)");
        });

        assertTrue(exception.isGuestException());
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
