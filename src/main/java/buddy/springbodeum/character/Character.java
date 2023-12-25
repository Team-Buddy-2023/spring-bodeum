package buddy.springbodeum.character;

import buddy.springbodeum.chat.data.Chat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "characters")
@Getter
@Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "character")
    private List<Chat> chat;

    public Character(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Character() {

    }
}