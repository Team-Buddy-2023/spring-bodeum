package buddy.springbodeum.chat.data;

import lombok.Data;

@Data
public class ChatDTO {
    private String question;
    private String answer;
    private String comment;
}
