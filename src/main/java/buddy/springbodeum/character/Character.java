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
    private String imageURL;
    private String firstQuestion;

//    @OneToMany(mappedBy = "character")
//    private List<Chat> chat;

    public Character(String name, String description, String imageURL, String firstQuestion) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.firstQuestion = firstQuestion;
    }

    public Character() {

    }
}
