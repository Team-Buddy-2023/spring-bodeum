package buddy.springbodeum.user.service;

import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class UserService {

    private static final String[] COLORS = {"빨간", "파란", "노란", "푸른", "하얀", "검은", "핑크", "보라", "주황", "회색"};
    private static final String[] ANIMALS = {"강아지", "고양이", "호랑이", "사자", "새", "토끼", "양", "돼지", "소", "말"};
    private static final String[] ADJECTIVES = {"강한", "빠른", "빛나는", "든든한", "단단한", "멋진", "예쁜", "행복한", "화려한", "똑똑한"};
    private static final String[] FRUITS = {"사과", "바나나", "오렌지", "포도", "수박", "딸기", "파인애플", "체리", "레몬", "라임"};

    private static final Random random = new Random();

    private final UserRepository userRepository;
    private final FluffyRepository fluffyRepository;

    public UserService(UserRepository userRepository, FluffyRepository fluffyRepository) {
        this.userRepository = userRepository;
        this.fluffyRepository = fluffyRepository;
    }

    public User kakaoLogin(String kakaoId, String email) {
        // Long 타입으로 변환
        Long kakaoIdLong = Long.parseLong(kakaoId);

        String nickname = getRandomNickName();

        // 닉네임이 이미 존재하는지 확인
        while (userRepository.existsByNickname(nickname)) {
            nickname = getRandomNickName(); // 중복된 경우 다시 랜덤 닉네임 생성
        }

        User user = new User(kakaoIdLong, nickname, email);
        if(validateDuplicateUser(user)) {
            userRepository.save(user);
        }
        return userRepository.findByKakaoId(kakaoIdLong);
    }

    private String getRandomNickName() {
        String color = getRandomElement(COLORS);
        String animal = getRandomElement(ANIMALS);
        String adjective = getRandomElement(ADJECTIVES);
        String fruit = getRandomElement(FRUITS);

        return color + animal + "의" + adjective + fruit;
    }

    private static String getRandomElement(String[] array) {
        int randomIndex = random.nextInt(array.length);
        return array[randomIndex];
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