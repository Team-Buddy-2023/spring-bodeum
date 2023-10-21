//package buddy.springbodeum.chat.controller;
//
//import buddy.springbodeum.chat.service.ChatService;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//public class ChatController {
//
//    private final ChatService chatService;
//
//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }
//
//    @PostMapping(value = "/chat/{characterId}")
//    public String createChatAnswer(@PathVariable Long characterId, @RequestBody Map<String, String> request) {
//        String question = request.get("question");
//        return chatService.createAnswer(characterId, question);
//    }
//
//    @PostMapping(value = "/chat/share")
//    public void shareChatAnswer(@RequestBody Map<String, Object> request) {
//        Long characterId = Long.parseLong(request.get("characterId").toString());
//        Long userId = Long.parseLong(request.get("userId").toString());
//
//        List<Map<String, String>> data = (List<Map<String, String>>) request.get("data");
//
//        for (Map<String, String> entry : data) {
//            String question = entry.get("question");
//            String answer = entry.get("answer");
//
//
//        }
//
//
//    }
//}
