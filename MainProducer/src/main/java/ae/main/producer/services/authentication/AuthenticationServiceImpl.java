package ae.main.producer.services.authentication;

import ae.main.producer.models.UserData;
import ae.main.producer.repositories.UserDataRepository;
import ae.main.producer.security.details.UserDetailsImpl;
import main.dto.LoginDto;
import main.dto.MainDto;
import main.dto.MainUtil;
import main.dto.RegistrationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public UserData getUserByAuthentication(Authentication authentication) {
        return userDataRepository.findById(getIdFromAuth(authentication)).get();
    }

    private String getIdFromAuth(Authentication authentication) {
        UserDetailsImpl currentUserDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserData currentUserModel = currentUserDetails.getUser();

        return currentUserModel.getId();
    }

    @Override
    public void authenticate(LoginDto loginDto){
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.LOGIN)
                .json(MainUtil.objectToJsonString(loginDto))
                .build();
        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }

    @Override
    public void register(RegistrationDto registrationDto) {
        MainDto mainDto = MainDto.builder()
                .action(MainDto.Action.REGISTER)
                .json(MainUtil.objectToJsonString(registrationDto))
                .build();
        rabbitTemplate.convertAndSend("mainProducerToAgregatorQueue", MainUtil.objectToByteArray(mainDto));
    }
}
