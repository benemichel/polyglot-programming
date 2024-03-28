import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.interfaces.InterfaceB;
import com.polyglot.demo.project.interfaces.WithOverloadedMethod;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class MethodOverloadingInPythonTest {
    

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }

    @Test
    public void importPythonObjectWithFakeOverloadedMethod() throws Exception {
        URL url = getClass().getResource("WithFakeOverloadedMethod.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);

        Value WithFakeOverloadedMethod = context.getBindings("python").getMember("WithFakeOverloadedMethod");
        WithOverloadedMethod withOverloadedMethod = WithFakeOverloadedMethod.as(WithOverloadedMethod.class);

        assertEquals("", withOverloadedMethod.concat());

        // ArrayList<String> list = new ArrayList<>();
        // list.add("foo");
        // list.add("bar");

        // TODO semms not possible because of *args to pass lst as first param

        assertEquals("foo", withOverloadedMethod.concat( "foo"));
        assertEquals("foobar", withOverloadedMethod.concat( "foo", "bar"));
        assertEquals("foo%i3", withOverloadedMethod.concat( "foo", 3));
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
