import spock.lang.Specification

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

class JavaAccessJsSpec extends Specification {

    private Context context

    def void setup() {
        context = Context.newBuilder().allowAllAccess(true).build()
    }

    def "semantics of null and undefined from js are lost in java"() {
        given:

        when:
        Value undefinedJs = context.eval('js', 'let foo; foo')
        Value nullJs = context.eval('js', 'foo')

        then:
        undefinedJs == nullJs
    }
}