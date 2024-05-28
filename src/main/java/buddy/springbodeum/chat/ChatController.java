package buddy.springbodeum.chat;

import buddy.springbodeum.chat.dto.*;
import buddy.springbodeum.fluffy.Fluffy;
import buddy.springbodeum.fluffy.FluffyRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.chat.service.ChatService;
import buddy.springbodeum.chat.service.GPTService;
import buddy.springbodeum.user.UserRepository;
import buddy.springbodeum.user.data.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
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

    @GetMapping(value = "/chat/{fluffyId}")
    public ChatAnswerDTO createChatAnswer(@PathVariable String fluffyId, @RequestParam String question) {
        return gptService.createAnswer(question, Long.valueOf(fluffyId));
    }

    @PostMapping(value = "/chat/share/{userId}")
    public ResponseEntity<String> shareChatAnswer(@PathVariable Long userId, @RequestBody ChatRequestDTO chatRequestDTO) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다");
        }

        Optional<Fluffy> optionalFluffy = fluffyRepository.findFluffyByName(chatRequestDTO.getFluffyName());
        if (optionalFluffy.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("플러피를 찾을 수 없습니다");
        }

        User user = optionalUser.get();
        Fluffy fluffy = optionalFluffy.get();
        LocalDateTime dateTime = chatRequestDTO.getDateTime();
        String question = chatRequestDTO.getQuestion();
        String answer = chatRequestDTO.getAnswer();
        String comment = chatRequestDTO.getComment();

        Chat chat = new Chat(question, answer, comment, dateTime, user, fluffy, 0);
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


    @PostMapping(value = "/chat/views/{chatId}")
    public void updateViews (@PathVariable Long chatId) {

    }
}