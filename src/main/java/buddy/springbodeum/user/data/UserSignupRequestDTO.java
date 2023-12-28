package buddy.springbodeum.user.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSignupRequestDTO {
    private Long kakaoId;
    private String nickname;
    private String email;

    @Builder
    public UserSignupRequestDTO(Long kakaoId, String nickname, String email) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
    }
}
