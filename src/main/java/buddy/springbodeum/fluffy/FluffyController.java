package buddy.springbodeum.fluffy;

import buddy.springbodeum.fluffy.dto.FluffyResponseDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/characters")
public class FluffyController {

    private final FluffyService fluffyService;

    public FluffyController(FluffyService fluffyService) {
        this.fluffyService = fluffyService;
    }

    // 메인화면 첫 로딩 시 캐릭터 정보를 가져옴
//    @RequestMapping(value = "/characters", method = RequestMethod.GET)
    @GetMapping(value = "")
    public List<FluffyResponseDTO> getAllFluffies() {
        List<Fluffy> fluffies = fluffyService.getAllFluffies();

        // FluffyResponseDTO로 변환하여 반환
        return fluffies.stream()
                .map(fluffy -> new FluffyResponseDTO(
                        fluffy.getId(),
                        fluffy.getName(),
                        fluffy.getDescription()
                ))
                .collect(Collectors.toList());
    }

    //캐릭터 저장(서버상에서)
    @GetMapping(value = "/delete")
    public String deleteFluffies() {
        fluffyService.deleteAll();
        return "delete fluffies";
    }

    @GetMapping(value = "/create")
    public String saveFluffies() {
        fluffyService.createFluffies();
        return "create fluffies";
    }
}
