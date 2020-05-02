package ae.data.storage.services.user;

import main.dto.LoginDto;
import main.dto.RegistrationDto;

public interface UserService {
    public void login(LoginDto loginDto);
    public void register(RegistrationDto registrationDto);
}
