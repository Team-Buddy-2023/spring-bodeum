package buddy.springbodeum.character.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "characters")
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String imageURL;

    public Character(String name, String description, String imageURL) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }

    public Character() {

    }
}
