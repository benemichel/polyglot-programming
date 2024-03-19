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

public class JavaAccessPythonTest {

    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder().allowAllAccess(true).build();
    }

    @Test
    public void castPythonClassToJavaInterfaceAndCallStaticMethod() throws Exception {
        URL url = getClass().getResource("classB.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);

        Value classB = context.getBindings("python").getMember("ClassB");
        InterfaceB interfaceB = classB.as(InterfaceB.class);
        int result = interfaceB.add(4, 15);

        assertEquals(19, result);
    }

    @Test
    public void instantiatePythonClassCastAndCallMethod() throws Exception {
        URL url = getClass().getResource("classA.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);

        Value classA = context.getBindings("python").getMember("ClassA");
        InterfaceA interfaceAImplementation = classA.newInstance().as(InterfaceA.class);
        int result = interfaceAImplementation.sum();

        assertEquals(5, result);
    }

    @Test
    public void implementServiceInPython() throws Exception {
        URL url = getClass().getResource("PythonServiceImpl.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);
        Value pythonServiceImpl = context.getBindings("python").getMember("PythonServiceImpl").newInstance();
        PythonService pythonService = pythonServiceImpl.as(PythonService.class);
        int result = pythonService.compute();

        assertEquals(2, result);

    }

    interface PythonService {
        int compute();

        void pythonConstructor(); // necessary, otherwise: java.lang.ClassCastException
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
