package ae.main.producer.services.authentication;

import ae.main.producer.models.UserData;
import main.dto.LoginDto;
import main.dto.RegistrationDto;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    UserData getUserByAuthentication(Authentication authentication);

    void authenticate(LoginDto loginDto);

    void register(RegistrationDto registrationDto);

}
