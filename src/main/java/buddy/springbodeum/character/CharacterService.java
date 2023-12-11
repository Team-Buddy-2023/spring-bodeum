package buddy.springbodeum.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> getAllCharacters() {
        // 모든 캐릭터를 데이터베이스에서 가져옵니다.
        return characterRepository.findAll();
    }

    public void createCharacters() {
        // 캐릭터 1
        Character character1 = new Character("토비", "따뜻한 마음을 지닌 활발한 토비\n당신에게 즐거운 웃음과 감동을 전해 비춰드릴게요", "http://example.com/image.jpg", "");
        characterRepository.save(character1);

        // 캐릭터 2
        Character character2 = new Character("마이로", "이성적이면서 현실적인 판단을 당신에게 전달해드릴 똑똑한 조언자 마이로에요", "http://example.com/image2.jpg", "");
        characterRepository.save(character2);

        // 캐릭터 3
        Character character3 = new Character("루미나", "감성적이고 창의적인 루미나\\n당신이 예상치 못한 답변으로 감동을 전달해드릴게요", "http://example.com/image3.jpg", "");
        characterRepository.save(character3);

        // 캐릭터 4
        Character character4 = new Character("블리", "누구에게나 사랑을 전달하며 온 세상을 따뜻하게 만드는 블리\\n당신에게 희망을 드릴게요", "http://example.com/image4.jpg", "");
        characterRepository.save(character4);
    }

    public void deleteAll() {
        characterRepository.deleteAllInBatch();
    }

    public Character getCharacter(Long characterId) {
        return characterRepository.findByCharacterId(characterId);
    }

}