package buddy.springbodeum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MostSharedFluffy {
    private String name;
    private String description;
    private int number;
}
