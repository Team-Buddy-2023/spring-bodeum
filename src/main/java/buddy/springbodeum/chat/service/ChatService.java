package buddy.springbodeum.chat.service;

import buddy.springbodeum.chat.repository.ChatRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public String createAnswer(String question) {

        return "";
    }
}
