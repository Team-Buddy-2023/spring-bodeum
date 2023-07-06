package buddy.springbodeum.service;

import buddy.springbodeum.domain.User;
import buddy.springbodeum.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean signUp(User newUser) {
        userRepository.save(newUser);
        return true;
    }

    public String randomNickname() {
        return "랜덤 닉네임";
    }

}
