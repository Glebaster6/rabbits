package ae.main.producer.services.authentication;

import ae.main.producer.models.UserData;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    UserData getUserByAuthentication(Authentication authentication);

}
