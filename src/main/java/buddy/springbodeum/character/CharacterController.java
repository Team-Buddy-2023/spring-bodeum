package buddy.springbodeum.character;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // 메인화면 첫 로딩 시 캐릭터 정보를 가져옴
//    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    @GetMapping(value = "/characters")
    public List<Character> getAllCharacters() {
        characterService.createCharacters();
        return characterService.getAllCharacters();
    }

    //캐릭터 저장(서버상에서)
    public void saveCharacters() {

    }
}

