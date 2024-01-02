import spock.lang.*

import com.polyglot.demo.project.entity.User;


class UserSpec extends Specification {

    def "a user can be created"() {
        given:
        String email = "test@examle.org";
        String password = "qwertz"

        when:
        User user = new User(email, password);

        then:
        user.getEmail() == email;
        user.getPassword() == password;
    }
}