package buddy.springbodeum.chat;

import buddy.springbodeum.chat.service.ChatService;
import buddy.springbodeum.chat.service.GPTService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class ChatController {

    private final ChatService chatService;
    private final GPTService gptService;

    public ChatController(ChatService chatService, GPTService gptService) {
        this.chatService = chatService;
        this.gptService = gptService;
    }

    @PostMapping(value = "/chat/{characterId}")
    public String createChatAnswer(@PathVariable Long characterId, @RequestBody String message) {
        return gptService.createAnswer(message, characterId);
    }

    @PostMapping(value = "/chat/share")
    public void shareChatAnswer(@RequestBody Map<String, Object> request) {
        Long characterId = Long.parseLong(request.get("characterId").toString());
        Long userId = Long.parseLong(request.get("userId").toString());

        List<Map<String, String>> data = (List<Map<String, String>>) request.get("data");

        for (Map<String, String> entry : data) {
            String question = entry.get("question");
            String answer = entry.get("answer");
        }
    }
}
