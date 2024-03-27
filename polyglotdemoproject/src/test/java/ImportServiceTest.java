import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.service.ImportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

public class ImportServiceTest {
    Context context;

    @BeforeEach
    public void setUp() {
        this.context = Context.newBuilder("ruby").allowAllAccess(true).build();
    }

    @Test
    public void findEanInFile() throws Exception {
        URL url = getClass().getResource("ImportServiceImpl.rb");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("ruby", file).build();

        ImportService importServiceImpl = context.eval(source).as(ImportService.class);

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classloader.getResourceAsStream("products.txt");

        String fileContent = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        String result = importServiceImpl.find_ean(fileContent);

        assertEquals("5901234123457", result);
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
