import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.entity.Product;
import com.polyglot.demo.project.enums.ProductCategories;
import com.polyglot.demo.project.interfaces.InterfaceA;
import com.polyglot.demo.project.interfaces.InterfaceB;
import com.polyglot.demo.project.service.ImportService;
import com.polyglot.demo.project.service.RecommendationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

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
        URL url = getClass().getResource("ClassB.py");
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
        URL url = getClass().getResource("ClassA.py");
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

    @Test
    public void callRecommendationServiceImpl() throws Exception {
        URL url = getClass().getResource("RecommendationServiceImpl.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);

        RecommendationService service = context
                .getBindings("python")
                .getMember("RecommendationServiceImpl")
                .as(RecommendationService.class);

        Product shoes = new Product("123456789", "shoes", ProductCategories.CLOTHES);
        Product tv = new Product("123456789", "tv", ProductCategories.ELECTRONICS);
        Product shirt = new Product("123456789", "shirt", ProductCategories.CLOTHES);
        Product desk = new Product("123456789", "desk", ProductCategories.FURNITURE);
        ArrayList<Product> products = new ArrayList<>();

        products.add(shoes);
        products.add(tv);
        products.add(shirt);
        products.add(desk);

        Product recommendedProduct = service.recommend(shoes, products);

        assertEquals(shoes, recommendedProduct);
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
