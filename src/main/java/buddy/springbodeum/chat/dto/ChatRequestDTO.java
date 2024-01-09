package buddy.springbodeum.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRequestDTO {
    private Long userId;
    private String fluffyName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dateTime;

    private String question;
    private String answer;
    private String comment;
}