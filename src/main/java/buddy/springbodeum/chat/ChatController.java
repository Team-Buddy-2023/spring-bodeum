package buddy.springbodeum.chat;

import buddy.springbodeum.chat.data.ChatDTO;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.chat.data.ChatRequestDTO;
import buddy.springbodeum.chat.service.ChatService;
import buddy.springbodeum.chat.service.GPTService;
import buddy.springbodeum.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
public class ChatController {

    private final ChatService chatService;
    private final GPTService gptService;
    private final UserRepository userRepository;
    private final FluffyRepository fluffyRepository;

    public ChatController(ChatService chatService, GPTService gptService, UserRepository userRepository, FluffyRepository fluffyRepository) {
        this.chatService = chatService;
        this.gptService = gptService;
        this.userRepository = userRepository;
        this.fluffyRepository = fluffyRepository;
    }

    @PostMapping(value = "/chat/{fluffyId}")
    public String createChatAnswer(@PathVariable Long fluffyId, @RequestBody String message) {
        return gptService.createAnswer(message, fluffyId);
    }

    @PostMapping(value = "/chat/share/{userId}")
    public ResponseEntity<String> shareChatAnswer(@PathVariable Long userId, @RequestBody ChatRequestDTO chatRequestDTO) {
        String fluffyName = chatRequestDTO.getFluffyName();
        LocalDateTime dateTime = chatRequestDTO.getDateTime();

        List<ChatDTO> chatList = chatRequestDTO.getChat();

        for (ChatDTO chatDTO : chatList) {
            String question = chatDTO.getQuestion();
            String answer = chatDTO.getAnswer();
            String comment = chatDTO.getComment();
            Chat chat = new Chat(question, answer,comment, dateTime, userRepository.findByUserId(userId), fluffyRepository.findFluffyByName(fluffyName));
            chatService.createChat(chat);
        }

        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 저장되었습니다.");
    }
}