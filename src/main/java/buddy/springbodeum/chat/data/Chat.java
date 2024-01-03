package buddy.springbodeum.chat.data;

import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.user.data.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fluffy_id")
    private Fluffy fluffy;

    public Chat(String question, String answer, String comment, LocalDateTime dateTime, User user, Fluffy fluffy) {
        this.question = question;
        this.answer = answer;
        this.comment = comment;
        this.dateTime = dateTime;
        this.user = user;
        this.fluffy = fluffy;
    }

    public Chat() {

    }
}