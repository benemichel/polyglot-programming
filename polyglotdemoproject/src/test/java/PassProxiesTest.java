import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

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

    class JavaArray implements ProxyArray {
        private final ArrayList<Integer> delegate;
     
        public JavaArray(ArrayList<Integer> delegate) {
            this.delegate = delegate;
        }
    
        public Object get(long index) {
            return delegate.get((int) index);
        }
        public void set(long index, Value value) {
            throw new UnsupportedOperationException();
        }
        public long getSize() {
            return delegate.size();
        }
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
