package buddy.springbodeum.chat.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ChatRequestDTO {
    private Long fluffyId;
    private Long userId;
    private LocalDateTime dateTime;
    private Map<String, String> questionAndAnswer;
}