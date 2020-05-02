package ae.main.producer.services.authentication;

import ae.main.producer.models.UserData;
import ae.main.producer.repositories.UserDataRepository;
import ae.main.producer.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    UserDataRepository userDataRepository;

    @Override
    public UserData getUserByAuthentication(Authentication authentication) {
        return userDataRepository.findById(getIdFromAuth(authentication)).get();
    }

    private String getIdFromAuth(Authentication authentication) {
        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserData currentUserModel = currentUserDetails.getUser();

        return currentUserModel.getId();
    }
}
