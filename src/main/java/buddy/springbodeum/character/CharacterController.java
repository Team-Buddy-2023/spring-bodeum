package buddy.springbodeum.character;

import buddy.springbodeum.character.dto.CharacterResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // 메인화면 첫 로딩 시 캐릭터 정보를 가져옴
//    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    @GetMapping(value = "")
    public List<CharacterResponseDTO> getAllCharacters() {
        List<Character> characters = characterService.getAllCharacters();

        // CharacterResponseDTO로 변환하여 반환
        return characters.stream()
                .map(character -> new CharacterResponseDTO(
                        character.getId(),
                        character.getName(),
                        character.getDescription()
                ))
                .collect(Collectors.toList());
    }

    //캐릭터 저장(서버상에서)
    @GetMapping(value = "/delete")
    public String deleteCharacters() {
        characterService.deleteAll();
        return "delete characters";
    }

    @GetMapping(value = "/create")
    public String saveCharacters() {
        characterService.createCharacters();
        return "create characters";
    }
}
