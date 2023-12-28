package buddy.springbodeum.user.data;

import lombok.Getter;

@Getter
public class UserResponseDTO {
    private final Long kakaoId;
    private final String nickname;
    private final String email;

    public UserResponseDTO(User user) {
        this.kakaoId = user.getKakaoId();
        this.nickname = user.getEmail();
        this.email = user.getNickname();
    }
}
