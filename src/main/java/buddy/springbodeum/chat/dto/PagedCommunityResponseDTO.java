package buddy.springbodeum.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class PagedCommunityResponseDTO {
    private List<CommunityResponseDTO> communityResponses;
    private long totalChats;

    public PagedCommunityResponseDTO(List<CommunityResponseDTO> communityResponses, long totalChats) {
        this.communityResponses = communityResponses;
        this.totalChats = totalChats;
    }
}
