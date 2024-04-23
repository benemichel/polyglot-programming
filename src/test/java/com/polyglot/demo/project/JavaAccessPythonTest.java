package com.polyglot.demo.project;

import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.entity.Product;
import com.polyglot.demo.project.enums.ProductTags;
import com.polyglot.demo.project.interfaces.InterfaceA;
import com.polyglot.demo.project.interfaces.InterfaceB;
import com.polyglot.demo.project.service.RecommendationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        context = Context.newBuilder().allowAllAccess(true).option("python.ForceImportSite", "true").build();
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
        URL url1 = getClass().getResource("/");
        File file1 = new File(url1.getPath());
        String path = file1.toPath().toString();

        Context context = Context.newBuilder("python")
        .allowAllAccess(true)
        .option("python.ForceImportSite", "true")
        .option("python.PythonPath", path)
        .option("python.Executable", "venv/bin/graalpy")
        .build();

        URL url = getClass().getResource("RecommendationServiceImpl.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        context.eval(source);

        RecommendationService service = context
                .getBindings("python")
                .getMember("RecommendationServiceImpl")
                .as(RecommendationService.class);

        List<ProductTags> shoeTags= Arrays.asList(ProductTags.FASHION, ProductTags.SALE, ProductTags.SUMMER);
        List<ProductTags> tvTags= Arrays.asList(ProductTags.SALE, ProductTags.ELECTRONICS);
        List<ProductTags> shirtTags= Arrays.asList(ProductTags.FASHION, ProductTags.SUMMER);
        List<ProductTags> deskTags= Arrays.asList( ProductTags.SALE);

        Product shoes = new Product("123456789", "shoes",  shoeTags);
        Product tv = new Product("123456789", "tv",  tvTags);
        Product shirt = new Product("123456789", "shirt",  shirtTags);
        Product desk = new Product("123456789", "desk",  deskTags);
        ArrayList<Product> products = new ArrayList<>();

        products.add(shoes);
        products.add(tv);
        products.add(shirt);
        products.add(desk);

        Product recommendedProduct = service.recommend(shoes, products);

        assertEquals(shirt, recommendedProduct);
    }

    @Test
    public void precisionIsRetained() throws Exception {
        URL url = getClass().getResource("ReceiveAndReturnFloat.py");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("python", file).build();

        double floatdoubleVal = 0.00000000001223456789d;
        context.getPolyglotBindings().putMember("floatingPointValue", floatdoubleVal);
        Value result = context.eval(source);

        assertTrue(result.fitsInDouble());

        assertEquals(floatdoubleVal, result.asDouble(), 0.00000000000000000001d);

        assertThrows(Exception.class, () -> {
            assertNotEquals(floatdoubleVal, result.asFloat(), 0.00000000000000000001d);
        });
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
