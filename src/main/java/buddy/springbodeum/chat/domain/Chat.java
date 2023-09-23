package buddy.springbodeum.chat.domain;

import buddy.springbodeum.user.domain.User;
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

    @ManyToOne
    private User user;

    @ElementCollection
    private List<String> question;

    @ElementCollection
    private List<String> answer;

    private LocalDateTime dateTime;

}
