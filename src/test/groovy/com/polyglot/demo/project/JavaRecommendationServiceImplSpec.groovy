import spock.lang.Specification

import com.polyglot.demo.project.entity.Product
import com.polyglot.demo.project.enums.ProductTags
import com.polyglot.demo.project.service.RecommendationService

import java.io.File
import java.net.URL
import java.util.ArrayList
import java.util.Arrays
import java.util.List

import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Source

class JavaRecommendationServiceImplSpec extends Specification {

    def "correct product is recommended"() {
        given:
        URL url1 = getClass().getResource('')
        File file1 = new File(url1.getPath())
        String path = file1.toPath().toString()

        Context context = Context.newBuilder('python')
        .allowAllAccess(true)
        .option('python.ForceImportSite', 'true')
        .option('python.PythonPath', path)
        .option('python.Executable', 'venv/bin/graalpy')
        .build()

        URL url = getClass().getResource('RecommendationServiceImpl.py')
        File file = new File(url.getPath())
        Source source = Source.newBuilder('python', file).build()

        context.eval(source)

        List<ProductTags> shoeTags = Arrays.asList(ProductTags.FASHION, ProductTags.SALE, ProductTags.SUMMER)
        List<ProductTags> tvTags = Arrays.asList(ProductTags.SALE, ProductTags.ELECTRONICS)
        List<ProductTags> shirtTags = Arrays.asList(ProductTags.FASHION, ProductTags.SUMMER)
        List<ProductTags> deskTags = Arrays.asList(ProductTags.SALE)

        Product shoes = new Product('123456789', 'shoes',  shoeTags)
        Product tv = new Product('123456789', 'tv',  tvTags)
        Product shirt = new Product('123456789', 'shirt',  shirtTags)
        Product desk = new Product('123456789', 'desk',  deskTags)
        ArrayList<Product> products = new ArrayList<>()

        products.add(shoes)
        products.add(tv)
        products.add(shirt)
        products.add(desk)

        when:
        RecommendationService service = context
                .getBindings('python')
                .getMember('RecommendationServiceImpl')
                .as(RecommendationService.class)

        Product recommendedProduct = service.recommend(shoes, products)

        then:
        shirt === recommendedProduct
    }
}
