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

    public User kakaoLogin(String kakaoId, String nickname, String email) {
        // Long 타입으로 변환
        Long kakaoIdLong = Long.parseLong(kakaoId);
        User user = new User(kakaoIdLong, nickname, email);
        if(validateDuplicateUser(user)) {
            userRepository.save(user);
        }
        return userRepository.findByKakaoId(kakaoIdLong);
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