package buddy.springbodeum.chat.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ChatRequestDTO {
    private Long characterId;
    private Long userId;
    private LocalDateTime dateTime;
    private Map<String, String> questionAndAnswer;
}
