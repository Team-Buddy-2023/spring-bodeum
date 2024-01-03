package buddy.springbodeum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatResponseDTO {
    private Long chatId;
    private String fluffyName;
    private String comment;
    private String answer;
    private LocalDateTime dateTime;
}
