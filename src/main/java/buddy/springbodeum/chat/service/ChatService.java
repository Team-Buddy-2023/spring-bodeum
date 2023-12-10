package buddy.springbodeum.chat.service;

import buddy.springbodeum.chat.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final GPTService gptService;

    public ChatService(ChatRepository chatRepository, GPTService gptService) {
        this.chatRepository = chatRepository;
        this.gptService = gptService;
    }

    public String createAnswer(Long characterId, String question) {
        return gptService.send(characterId, question);
    }
}
