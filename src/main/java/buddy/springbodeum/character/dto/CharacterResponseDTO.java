package buddy.springbodeum.character.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterResponseDTO {
    private Long id;
    private String name;
    private String description;

    public CharacterResponseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
