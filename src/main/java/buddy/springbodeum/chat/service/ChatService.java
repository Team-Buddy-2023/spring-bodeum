package buddy.springbodeum.chat.service;

import buddy.springbodeum.chat.ChatRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.chat.dto.CommunityResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final GPTService gptService;

    public ChatService(ChatRepository chatRepository, GPTService gptService) {
        this.chatRepository = chatRepository;
        this.gptService = gptService;
    }

    public void createChat(Chat chat) {
        chatRepository.save(chat);
    }

    public List<CommunityResponseDTO> getCommunityChatList() {
        List<Chat> chats = chatRepository.findAll();

        List<CommunityResponseDTO> communityResponseList = new ArrayList<>();

        for (Chat chat : chats) {
            Long chatId = chat.getId();
            Long userId = chat.getUser().getUserId();
            String nickname = chat.getUser().getNickname();
            String comment = chat.getComment();
            String fluffyName = chat.getFluffy().getName();
            LocalDateTime dateTime = chat.getDateTime();
            String answer = chat.getAnswer();

            CommunityResponseDTO communityResponseDTO = new CommunityResponseDTO(chatId, userId, nickname, comment, fluffyName, dateTime, answer);
            communityResponseList.add(communityResponseDTO);
        }

        return communityResponseList;
    }

    public void deleteChat(Long chatId) {
        chatRepository.deleteById(chatId);
    }
}