package buddy.springbodeum.chat.data;

import buddy.springbodeum.character.Character;
import buddy.springbodeum.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "chat")
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;

    public Chat(String question, String answer, LocalDateTime dateTime, User user, Character character) {
        this.question = question;
        this.answer = answer;
        this.dateTime = dateTime;
        this.user = user;
        this.character = character;
    }
}
