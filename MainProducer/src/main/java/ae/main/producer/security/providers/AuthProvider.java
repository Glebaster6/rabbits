package ae.main.producer.security.providers;

import ae.main.producer.models.UserData;
import ae.main.producer.repositories.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Component
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    UserDataRepository usersRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials()
                .toString();

        Optional<UserData> optionalUser = usersRepository.findById(username);
        while (!optionalUser.isPresent()){
            optionalUser = usersRepository.findById(username);
        }
        UserData userData = optionalUser.get();

        if (!userData.getIsCorrect()){
            usersRepository.delete(userData);
            throw new BadCredentialsException("Wrong password or login");
        }

        userData.setLastUsed(Instant.now());
        usersRepository.save(userData);

        UserDetails details = userDetailsService.loadUserByUsername(username);
        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
        return new UsernamePasswordAuthenticationToken(details, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
