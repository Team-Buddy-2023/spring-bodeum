package buddy.springbodeum.fluffy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FluffyResponseDTO {
    private Long id;
    private String name;
    private String description;

    public FluffyResponseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
