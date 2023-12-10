package buddy.springbodeum.chat.service;

import buddy.springbodeum.chat.ChatRepository;
import buddy.springbodeum.chat.data.Chat;
import org.springframework.stereotype.Service;

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
}
