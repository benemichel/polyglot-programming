import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import com.polyglot.demo.project.interfaces.AbstractPythonC;


public class MultiInheritanceTest {


    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }

    @Test
    public void useObjectWithMultiInheritanceInJava() throws Exception {

        URL url = getClass().getResource("MultiInheritance.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        Value c = context.eval(source).getMember("c");
        AbstractPythonC abstractC = assertDoesNotThrow(() -> {
            return c.as(AbstractPythonC.class); 
        });

        assertEquals("class A", abstractC.print());
        assertNotEquals("class B", abstractC.print());
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
    
}
