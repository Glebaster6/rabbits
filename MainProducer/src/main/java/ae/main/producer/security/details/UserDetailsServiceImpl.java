package ae.main.producer.security.details;

import ae.main.producer.models.UserData;
import ae.main.producer.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDataRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDataRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<UserData> optionalUser = usersRepository.findById(login);
        while (!optionalUser.isPresent()) {
            optionalUser = usersRepository.findById(login);
        }

        UserData userData = optionalUser.get();
        userData.setLastUsed(Instant.now());
        usersRepository.save(userData);

        return new UserDetailsImpl(userData);
    }
}
