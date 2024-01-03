package buddy.springbodeum.user.service;

import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.UserRepository;
import buddy.springbodeum.user.dto.ChatResponseDTO;
import buddy.springbodeum.user.dto.MostSharedFluffy;
import buddy.springbodeum.user.dto.MyPageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UserService {

    private static final String[] COLORS = {"빨간", "파란", "노란", "푸른", "하얀", "검은", "핑크", "보라", "주황", "회색"};
    private static final String[] ANIMALS = {"강아지", "고양이", "호랑이", "사자", "새", "토끼", "양", "돼지", "소", "말"};
    private static final String[] ADJECTIVES = {"강한", "빠른", "빛나는", "든든한", "단단한", "멋진", "예쁜", "행복한", "화려한", "똑똑한"};
    private static final String[] FRUITS = {"사과", "바나나", "오렌지", "포도", "수박", "딸기", "파인애플", "체리", "레몬", "라임"};

    private Map<String, Integer> fluffyCountMap = initializeFluffyCountMap();

    private Map<String, Integer> initializeFluffyCountMap() {
        // 초기화할 플러피 이름들
        List<String> initialFluffyNames = Arrays.asList("토비", "마이로", "루미나", "블리");

        // 플러피 이름을 기준으로 그룹화하여 개수를 세는 Map 생성
        return initialFluffyNames.stream()
                .collect(Collectors.toMap(Function.identity(), name -> 0));
    }

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

    public MyPageResponseDTO getMyPage(Long userId) {
        User user = userRepository.findByUserId(userId);
        String nickname = user.getNickname();
        String email = user.getEmail();
        String favoriteFluffyName = null;

        Fluffy fluffy = user.getFavoriteFluffy();
        if (fluffy != null) {
            favoriteFluffyName = fluffy.getName();
        }


        List<Chat> chatList = user.getChat();

        // ChatResponseDTO 리스트 생성
        List<ChatResponseDTO> chat = chatList.stream()
                .map(c -> new ChatResponseDTO(
                        c.getFluffy().getName(),
                        c.getComment(),
                        c.getAnswer(),
                        c.getDateTime()
                ))
                .collect(Collectors.toList());



        // chatList를 기반으로 실제 값으로 업데이트
        chatList.stream()
                .collect(Collectors.groupingBy(c -> c.getFluffy().getName(), Collectors.counting()))
                .forEach((name, count) -> fluffyCountMap.put(name, count.intValue()));


        if (!fluffyCountMap.values().stream().allMatch(count -> count == 0)) {
            updateFluffyCountMap(fluffyCountMap);
        }

        // MostSharedFluffy 리스트 생성
        List<MostSharedFluffy> mostSharedFluffy = chatList.stream()
                .collect(Collectors.groupingBy(c -> c.getFluffy().getName(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) // 개수가 많은 순서로 정렬
                .map(entry -> new MostSharedFluffy(
                        entry.getKey(),
                        getFluffyDescription(entry.getKey()),  // getFluffyDescription 메소드로 해당 플러피의 description 가져오기
                        fluffyCountMap.get(entry.getKey())  // fluffyCountMap에서 해당 플러피의 number값 가져오기
                ))
                .collect(Collectors.toList());

        return new MyPageResponseDTO(nickname, email, favoriteFluffyName, chat, mostSharedFluffy);
    }

    private void updateFluffyCountMap(Map<String, Integer> fluffyCountMap) {
        int currentValue = 1;

        // fluffyCountMap의 value값이 큰 순서대로 1, 2, 3, 4로 변경
        for (Map.Entry<String, Integer> entry : fluffyCountMap.entrySet()) {
            entry.setValue(currentValue);
            currentValue++;
        }

        // fluffyCountMap의 value값이 2개의 key값에서 동일하다면 그 두 value값은 동일한 값으로 변경
        Map<Integer, List<String>> reverseMap = fluffyCountMap.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        for (List<String> names : reverseMap.values()) {
            if (names.size() > 1) {
                int commonValue = fluffyCountMap.get(names.get(0));
                names.forEach(name -> fluffyCountMap.put(name, commonValue));
            }
        }
    }

    private String getFluffyDescription(String fluffyName) {
        Fluffy fluffy = fluffyRepository.findByName(fluffyName);
        return fluffy.getDescription();
    }

}