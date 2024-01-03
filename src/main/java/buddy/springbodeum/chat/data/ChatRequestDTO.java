package buddy.springbodeum.chat.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatRequestDTO {
    private Long userId;
    private String fluffyName;
    private LocalDateTime dateTime;
    private List<ChatDTO> chat;
}