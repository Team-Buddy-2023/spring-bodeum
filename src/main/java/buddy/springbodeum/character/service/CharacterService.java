package buddy.springbodeum.character.service;

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
        return characterRepository.findAll();
    }

}
