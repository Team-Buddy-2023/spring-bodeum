package buddy.springbodeum.chat;

import buddy.springbodeum.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/chat/{characterId}")
    public String createChatAnswer(@PathVariable Long characterId, @RequestBody Map<String, String> request) {
        String question = request.get("question");
        return chatService.createAnswer(characterId, question);
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

    @Value("${chatgpt.api-key}")
    private String key;
    @PostMapping("/chat/send")
    public ResponseEntity send(@RequestBody String message) {

        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openai.com/v1/chat/completions")
                .build()
                .encode()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + key);

        ArrayList<Message> list = new ArrayList<>();

        list.add(new Message("system","[Role] : I want you to act as a Philosophy.\n[Results] : Answer my + \"" + message + "\" in Korean in 200 characters or less with a metaphorical tone. Be sure to review and make suggestions to make sure your tone isn't awkward. ##Do not remind me what I asked you for## 그리고 대답은 친구처럼 반말로 대답해줘"));

        Body body = new Body("gpt-3.5-turbo", list);

        RequestEntity<Body> httpEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, uri);

        return restTemplate.exchange(httpEntity, String.class);
    }

    @AllArgsConstructor
    @Data
    static class Body {
        String model;
        List<Message> messages;
    }

    @AllArgsConstructor
    @Data
    static class Message {
        String role;
        String content;
    }
}
