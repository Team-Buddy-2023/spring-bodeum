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

        // 캐릭터 생성
        createCharacters();

        // 모든 캐릭터를 데이터베이스에서 가져옵니다.
        return characterRepository.findAll();
    }

    public void createCharacters() {
        // 가상의 캐릭터 데이터를 생성하고 데이터베이스에 저장합니다.
        List<Character> characters = new ArrayList<>();

        // 캐릭터 1
        Character character1 = new Character("토비", "따뜻한 마음을 지닌 활발한 토비 당신에게 즐거운 웃음과 감동을 전해 비춰드릴게요", "http://example.com/image.jpg");
        characters.add(character1);

        // 캐릭터 2
        Character character2 = new Character("마이로", "", "http://example.com/image2.jpg");
        characters.add(character2);

        // 추가적인 캐릭터 정보를 생성하고 characters 리스트에 추가할 수 있습니다.

        // 데이터베이스에 저장
        characterRepository.save(characters);
    }

}
