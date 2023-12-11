package buddy.springbodeum.user.service;

import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User kakaoLogin(Object nickname, Object email) {
        User user = new User((String) nickname, (String) email);
        if(validateDuplicateUser(user)) {
            userRepository.save(user);
        }
        return user;
    }

    private boolean validateDuplicateUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            System.out.println("이메일이 중복되었습니다.");
            return false;
        } else {
            return true;
        }
    }
}
