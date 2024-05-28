package buddy.springbodeum.chat.dto;

import lombok.Data;

@Data
public class viewsResponseDTO {
    private Long chatId; //조회한 공유 답변 아이디
    private Integer views; // 증가 후 조회수

    public viewsResponseDTO(Long id, Integer views) {
        this.chatId = id;
        this.views = views;
    }
}
