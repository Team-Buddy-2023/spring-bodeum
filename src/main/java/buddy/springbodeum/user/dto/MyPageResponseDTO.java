package buddy.springbodeum.user.dto;

import buddy.springbodeum.chat.data.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponseDTO {
    private String nickname;
    private String email;
    private String favoriteFluffyName;
    private List<ChatResponseDTO> chat;
    private List<MostSharedFluffy> mostSharedFluffy;
    private String imageURL;
}
