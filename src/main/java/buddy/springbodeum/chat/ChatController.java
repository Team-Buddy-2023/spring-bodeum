package buddy.springbodeum.chat;

import buddy.springbodeum.character.CharacterRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.chat.data.ChatRequestDTO;
import buddy.springbodeum.chat.service.ChatService;
import buddy.springbodeum.chat.service.GPTService;
import buddy.springbodeum.user.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
public class ChatController {

    private final ChatService chatService;
    private final GPTService gptService;
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    public ChatController(ChatService chatService, GPTService gptService, UserRepository userRepository, CharacterRepository characterRepository) {
        this.chatService = chatService;
        this.gptService = gptService;
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
    }

    @PostMapping(value = "/chat/{characterId}")
    public String createChatAnswer(@PathVariable Long characterId, @RequestBody String message) {
        return gptService.createAnswer(message, characterId);
    }

    @PostMapping(value = "/chat/share")
    public void shareChatAnswer(@RequestBody ChatRequestDTO chatRequestDTO) {
        Long characterId = chatRequestDTO.getCharacterId();
        Long userId = chatRequestDTO.getUserId();
        LocalDateTime dateTime = chatRequestDTO.getDateTime();
        Map<String, String> questionAndAnswer = chatRequestDTO.getQuestionAndAnswer();

        for (Map.Entry<String, String> entry : questionAndAnswer.entrySet()) {
            String question = entry.getKey();
            String answer = entry.getValue();
            Chat chat = new Chat(question, answer, dateTime, userRepository.findByUserId(userId), characterRepository.findByCharacterId(characterId));
            chatService.createChat(chat);
        }

    }
}
