import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.interfaces.InterfaceA;
import com.polyglot.demo.project.interfaces.InterfaceB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class PythonAccessJavaTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder("python", "js").allowAllAccess(true).build();
    }

    // TODO: kann ich auf eine private Java Methode in python zugreifen, wie kann ich Zugriff steuern -> Annotationen!!


    // TODO



    @Test
    public void accessJavaStandardClassAndCallMethod() throws Exception {
        URL url = getClass().getResource("JavaMath.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        Value result = context.eval(source);
        int resultInt = result.asInt();

        assertEquals(2, resultInt);
    }

    @Test
    public void accessCustomJavaClass() throws Exception {
        URL url = getClass().getResource("CalculateRectangle.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        Value result = context.eval(source);
        float resultFloat = result.asFloat();

        assertEquals(10.0, resultFloat, 0.0);
    }

    @Test
    public void callJsCode() throws Exception {
        URL url = getClass().getResource("CallJavaScript.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        Value result = context.eval(source);
        int resultInt = result.asInt();

        assertEquals(42, resultInt);
    }






    @AfterEach
    public void tearDown() {
        context.close();
    }
}
