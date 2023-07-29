package buddy.springbodeum.service.user;

import buddy.springbodeum.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository; //(1)

}
