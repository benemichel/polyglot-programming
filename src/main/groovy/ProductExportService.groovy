import com.polyglot.demo.project.entity.Product;

class ProductExportService {

    def String createXml(Product product) {
        def writer = new StringWriter();
        def xml = new groovy.xml.MarkupBuilder(writer);
        xml.product(id:1) {
            name(product.name)
            ean(product.ean13)
        }
        return writer.toString();
    }
}