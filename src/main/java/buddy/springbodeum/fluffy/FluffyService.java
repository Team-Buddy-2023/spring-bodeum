package buddy.springbodeum.fluffy;

import buddy.springbodeum.chat.ChatRepository;
import buddy.springbodeum.user.UserRepository;
import buddy.springbodeum.user.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FluffyService {
    private final FluffyRepository fluffyRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public FluffyService(FluffyRepository fluffyRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.fluffyRepository = fluffyRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public List<Fluffy> getAllFluffys() {
        // 모든 캐릭터를 데이터베이스에서 가져옵니다.
        return fluffyRepository.findAll();
    }

    public void createFluffys() {
        // 캐릭터 1
        Fluffy fluffy1 = new Fluffy("토비", "따뜻한 마음을 지닌 활발한 토비\n당신에게 즐거운 웃음과 감동을 전해 비춰드릴게요");
        fluffyRepository.save(fluffy1);

        // 캐릭터 2
        Fluffy fluffy2 = new Fluffy("마이로", "이성적이면서 현실적인 판단을 당신에게 전달해드릴 똑똑한 조언자 마이로에요");
        fluffyRepository.save(fluffy2);

        // 캐릭터 3
        Fluffy fluffy3 = new Fluffy("루미나", "감성적이고 창의적인 루미나\n당신이 예상치 못한 답변으로 감동을 전달해드릴게요");
        fluffyRepository.save(fluffy3);

        // 캐릭터 4
        Fluffy fluffy4 = new Fluffy("블리", "누구에게나 사랑을 전달하며 온 세상을 따뜻하게 만드는 블리\n당신에게 희망을 드릴게요");
        fluffyRepository.save(fluffy4);
    }

    public void deleteAll() {
        // 모든 Fluffy에 해당하는 Chat 삭제
        List<Fluffy> fluffies = fluffyRepository.findAll();
        for (Fluffy fluffy : fluffies) {
            chatRepository.deleteByFluffy(fluffy);

            List<User> usersWithFavoriteFluffy = userRepository.findByFavoriteFluffy(fluffy);
            for (User user : usersWithFavoriteFluffy) {
                if (user.getFavoriteFluffy() != null && user.getFavoriteFluffy().equals(fluffy)) {
                    user.setFavoriteFluffy(null);
                }
            }
        }
        // 모든 Fluffy 삭제
        fluffyRepository.deleteAll();
    }

    public Fluffy getFluffy(Long fluffyId) {
        return fluffyRepository.findFluffyById(fluffyId);
    }

}