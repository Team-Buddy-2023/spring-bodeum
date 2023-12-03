package buddy.springbodeum.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;

    public User() {

    }

    public User(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
