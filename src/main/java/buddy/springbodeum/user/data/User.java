package buddy.springbodeum.user.data;

import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.chat.data.Chat;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long kakaoId;
    private String nickname;
    private String email;
    private String imageURL;

    @OneToMany(mappedBy = "user")
    private List<Chat> chat;

    @OneToOne
    private Fluffy favoriteFluffy;

    public User(Long kakaoId, String nickname, String email, String imageURL) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
        this.imageURL = imageURL;
    }
}