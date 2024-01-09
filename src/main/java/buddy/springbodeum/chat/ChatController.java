package buddy.springbodeum.chat;

import buddy.springbodeum.chat.dto.*;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.chat.service.ChatService;
import buddy.springbodeum.chat.service.GPTService;
import buddy.springbodeum.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


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
    public ChatAnswerDTO createChatAnswer(@PathVariable Long fluffyId, @RequestBody ChatQuestionDTO question) {
        return gptService.createAnswer(question.getQuestion(), fluffyId);
    }

    @PostMapping(value = "/chat/share/{userId}")
    public ResponseEntity<String> shareChatAnswer(@PathVariable Long userId, @RequestBody ChatRequestDTO chatRequestDTO) {
        String fluffyName = chatRequestDTO.getFluffyName();
        LocalDateTime dateTime = chatRequestDTO.getDateTime();

        String question = chatRequestDTO.getQuestion();
        String answer = chatRequestDTO.getAnswer();
        String comment = chatRequestDTO.getComment();

        Chat chat = new Chat(question, answer,comment, dateTime, userRepository.findByUserId(userId), fluffyRepository.findFluffyByName(fluffyName));

        chatService.createChat(chat);

        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 저장되었습니다.");
    }

    @GetMapping(value = "/chat/community")
    public List<CommunityResponseDTO> getCommunityChatList() {
        return chatService.getCommunityChatList();
    }

    @DeleteMapping(value = "/chat/{chatId}")
    public ResponseEntity<String> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 삭제되었습니다.");
    }
}