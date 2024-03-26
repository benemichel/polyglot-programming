import org.junit.jupiter.api.Test;

import com.polyglot.demo.project.service.ImportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void findEanInFile() throws Exception{
        URL url = getClass().getResource("ImportServiceImpl.rb");
        File file = new File(url.getPath());
        Source source = Source.newBuilder("ruby", file).build();

        context.eval(source);

        ImportService importServiceImpl =  context
                .getBindings("ruby")
                .getMember("importServiceImpl")
                .as(ImportService.class);

        String filePath = "products.txt";
        String content = Files.readString(Paths.get(filePath));
            
        String fileContent = content;
        String result =  importServiceImpl.find_ean(fileContent);
        
        assertEquals("123456789", result);
    }

    @AfterEach
    public void tearDown() {
        context.close();
    }
}
