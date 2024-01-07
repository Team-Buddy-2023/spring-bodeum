package buddy.springbodeum.chat.dto;

import lombok.Data;

@Data
public class ChatAnswerDTO {
    private String answer;

    public ChatAnswerDTO(String answer) {
        this.answer = answer;
    }
}
