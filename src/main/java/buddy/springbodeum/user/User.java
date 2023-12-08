package buddy.springbodeum.user;

import lombok.*;

import javax.persistence.*;

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

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
