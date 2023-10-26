package buddy.springbodeum.character.controller;

import buddy.springbodeum.character.domain.Character;
import buddy.springbodeum.character.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // 메인화면 첫 로딩 시 캐릭터 정보를 가져옴
//    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    @GetMapping(value = "15.165.177.182:8080/characters")
    public List<Character> getAllCharacters() {
        System.out.println("test");
        characterService.createCharacters();
        System.out.println("test1");
        return characterService.getAllCharacters();
    }

    //캐릭터 저장(서버상에서)
    public void saveCharacters() {

    }
}

