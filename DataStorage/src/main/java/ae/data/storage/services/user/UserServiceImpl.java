package ae.data.storage.services.user;

import ae.data.storage.models.hot.Facility;
import ae.data.storage.models.hot.User;
import ae.data.storage.models.redis.UserData;
import ae.data.storage.repositories.hot.FacilityRepository;
import ae.data.storage.repositories.hot.UserRepository;
import ae.data.storage.repositories.redis.UserDataRepository;
import main.dto.LoginDto;
import main.dto.RegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Override
    public void login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByLogin(loginDto.getLogin());

        if (!optionalUser.isPresent()) {
            userDataRepository.save(UserData.builder()
                    .id(loginDto.getLogin())
                    .isCorrect(false)
                    .build());
        } else {
            User user = optionalUser.get();
            userDataRepository.save(UserData.builder()
                    .id(loginDto.getLogin())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .facilityId(user.getFacility().getId())
                    .userId(user.getId())
                    .isCorrect(true)
                    .build());

        }
    }

    @Override
    public void register(RegistrationDto registrationDto) {
        Facility facility = Facility.builder()
                .name(registrationDto.getFacilityName())
                .description(registrationDto.getFacilityDescription())
                .build();

        facility = facilityRepository.save(facility);

        User user = User.builder()
                .login(registrationDto.getLogin())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .role("EVALUATOR")
                .facility(facility)
                .build();

        user = userRepository.save(user);

        userDataRepository.save(
                UserData.builder()
                        .id(user.getLogin())
                        .password(user.getPassword())
                        .role(user.getRole())
                        .facilityId(user.getFacility().getId())
                        .userId(user.getId())
                        .isCorrect(true)
                        .build()
        );
    }
}
