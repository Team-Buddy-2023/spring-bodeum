package buddy.springbodeum.user.service;

import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.UserRepository;
import buddy.springbodeum.user.dto.ChatResponseDTO;
import buddy.springbodeum.user.dto.MostSharedFluffy;
import buddy.springbodeum.user.dto.MyPageResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Value("${bucket}")
    private String BUCKET;


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

        if (validateDuplicateUser(kakaoIdLong)) { //등록되지 않은 사용자일 때
            String nickname = getRandomNickName();

            // 닉네임이 이미 존재하는지 확인
            while (userRepository.existsByNickname(nickname)) {
                nickname = getRandomNickName(); // 중복된 경우 다시 랜덤 닉네임 생성
            }

            User user = new User(kakaoIdLong, nickname, email, getProfileRandomImage());
            userRepository.save(user);
        }

        return userRepository.findByKakaoId(kakaoIdLong);
    }

    private String getProfileRandomImage() {
        String s3BaseURL = "https://" + BUCKET + ".s3.ap-northeast-2.amazonaws.com/";

        // 1에서 5까지의 랜덤한 숫자 생성
        int randomNumber = new Random().nextInt(5) + 1;

        return s3BaseURL + "IMG_" + randomNumber + ".PNG";
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


    private boolean validateDuplicateUser(Long kakaoId) {
        User existingUser = userRepository.findByKakaoId(kakaoId);

        if (existingUser != null) {
            System.out.println("카카오 아이디가 중복되었습니다.");
            return false;
        }

        return true;
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

    public MyPageResponseDTO getMyPage(Long userId) {
        User user = userRepository.findByUserId(userId);
        String nickname = user.getNickname();
        String email = user.getEmail();
        String favoriteFluffyName = Optional.ofNullable(user.getFavoriteFluffy())
                .map(Fluffy::getName)
                .orElse(null);

        List<Chat> chatList = user.getChat();

        // ChatResponseDTO 리스트 생성
        List<ChatResponseDTO> chat = createChatResponseDTOList(chatList);


        List<Fluffy> allFlufys = fluffyRepository.findAll();

        List<MostSharedFluffy> mostSharedFluffyList = createMostSharedFluffyList(chatList, allFlufys);

        // MostSharedFluffyList의 number 수정
        int currentNumber = 0;
        int currentCount = 0;

        boolean allNumbersAreZero = mostSharedFluffyList.stream()
                .allMatch(mostSharedFluffy -> mostSharedFluffy.getNumber() == 0);

        if(allNumbersAreZero) {
            return new MyPageResponseDTO(nickname, email, favoriteFluffyName, chat, mostSharedFluffyList);
        }

        for (MostSharedFluffy mostSharedFluffy : mostSharedFluffyList) {
            if (mostSharedFluffy.getNumber() != currentCount) {
                currentCount = mostSharedFluffy.getNumber();
                currentNumber++;
            }
            mostSharedFluffy.setNumber(currentNumber);
        }

        return new MyPageResponseDTO(nickname, email, favoriteFluffyName, chat, mostSharedFluffyList);
    }

    private List<ChatResponseDTO> createChatResponseDTOList(List<Chat> chatList) {
        return chatList.stream()
                .map(c -> new ChatResponseDTO(
                        c.getId(),
                        c.getFluffy().getName(),
                        c.getComment(),
                        c.getAnswer(),
                        c.getDateTime()
                ))
                .collect(Collectors.toList());
    }

    private List<MostSharedFluffy> createMostSharedFluffyList(List<Chat> chatList, List<Fluffy> allFluffys) {
        Map<String, Long> fluffyCountMap = chatList.stream()
                .map(Chat::getFluffy)
                .map(Fluffy::getName)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return allFluffys.stream()
                .map(fluffy -> new MostSharedFluffy(
                        fluffy.getName(),
                        fluffy.getDescription(),
                        fluffyCountMap.getOrDefault(fluffy.getName(), 0L).intValue()
                ))
                .collect(Collectors.toList());
    }

    public boolean validateDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}