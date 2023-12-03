package buddy.springbodeum.character.service;

import buddy.springbodeum.character.domain.Character;
import buddy.springbodeum.character.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        System.out.println("test3");
        return characterRepository.findAll();
    }

    public void createCharacters() {
        // 캐릭터 1
        Character character1 = new Character("토비", "따뜻한 마음을 지닌 활발한 토비 당신에게 즐거운 웃음과 감동을 전해 비춰드릴게요", "http://example.com/image.jpg", "");
        characterRepository.save(character1);

        // 캐릭터 2
        Character character2 = new Character("마이로", "", "http://example.com/image2.jpg", "");
        characterRepository.save(character2);

        System.out.println("test2");
    }

}
