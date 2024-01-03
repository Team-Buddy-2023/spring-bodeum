package buddy.springbodeum.chat.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityResponseDTO {
    private Long chatId;
    private Long userId;
    private String nickname;
    private String comment;
    private String fluffyName;
    private LocalDateTime dateTime;
    private String answer;

    public CommunityResponseDTO(Long chatId, Long userId, String nickname, String comment, String fluffyName, LocalDateTime dateTime, String answer) {
        this.chatId = chatId;
        this.userId = userId;
        this.nickname = nickname;
        this.comment = comment;
        this.fluffyName = fluffyName;
        this.dateTime = dateTime;
        this.answer = answer;
    }
}
