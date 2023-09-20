package buddy.springbodeum.character.controller;

import buddy.springbodeum.character.domain.Character;
import buddy.springbodeum.character.service.CharacterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // 메인화면 첫 로딩 시 캐릭터 정보를 가져옴
    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    public List<Character> getAllCharacters() {
        return characterService.getAllCharacters();
    }
}
