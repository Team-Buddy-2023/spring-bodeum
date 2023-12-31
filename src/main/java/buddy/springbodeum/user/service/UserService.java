package buddy.springbodeum.user.service;

import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final FluffyRepository fluffyRepository;

    public UserService(UserRepository userRepository, FluffyRepository fluffyRepository) {
        this.userRepository = userRepository;
        this.fluffyRepository = fluffyRepository;
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

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updateMyPage(Long userId, String nickname, String favoriteFluffyName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(userId + " 사용자를 찾을 수 없습니다."));

        if (nickname != null) {
            user.setNickname(nickname);
        }

        if (favoriteFluffyName != null) {
            Fluffy favoriteFluffy = fluffyRepository.findByName(favoriteFluffyName);
            user.setFavoriteFluffy(favoriteFluffy);
        }

        userRepository.save(user);
    }
}