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

public class JavaAccessRubyTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }


    @Test
    public void callPrivateMethodDoesNotThrow() throws Exception{
        URL url = getClass().getResource("withPrivateMethod.rb");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("ruby", file).build();

        self.assertDoesNotThrow()

        Value withPrivateMethod = context.eval(source);

        withPrivateMethod.getMember("private_method").execute();
    }

    @Test
    public void callPublicMethodDoesNotThrow() throws Exception {
        URL url = getClass().getResource("withPrivateMethod.rb");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("ruby", file).build();

        Value withPrivateMethod = context.eval(source);

        withPrivateMethod.getMember("public_method").execute();
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
