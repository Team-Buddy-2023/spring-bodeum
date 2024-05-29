package buddy.springbodeum.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityResponseDTO {
    private Long chatId;
    private Long userId;
    private String nickname;
    private String comment;
    private String fluffyName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String answer;
    private Integer views;
    private String imageURL;

    public CommunityResponseDTO(Long chatId, Long userId, String nickname, String comment, String fluffyName, LocalDateTime dateTime, String answer, Integer views, String imageURL) {
        this.chatId = chatId;
        this.userId = userId;
        this.nickname = nickname;
        this.comment = comment;
        this.fluffyName = fluffyName;
        this.dateTime = dateTime;
        this.answer = answer;
        this.views = views;
        this.imageURL = imageURL;
    }
}
