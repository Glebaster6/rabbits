package ae.data.storage.services.user;

import ae.data.storage.repositories.hot.UserRepository;
import ae.data.storage.repositories.redis.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;



}
