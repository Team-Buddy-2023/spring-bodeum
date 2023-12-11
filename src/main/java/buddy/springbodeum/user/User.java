package buddy.springbodeum.user;

import buddy.springbodeum.chat.data.Chat;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Chat> chat;

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}