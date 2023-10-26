package buddy.springbodeum.chat.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPTService {

//    @Value("${chatgpt.api-key}")
    private String key;

    public String send(Long characterId, String message) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://api.openai.com/v1/chat/completions")
//                .build()
//                .encode()
//                .toUri();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + key);
//
//        ArrayList<Message> list = new ArrayList<>();
//
//        list.add(new Message("system","[Role] : I want you to act as a Philosophy.\n[Results] : Answer my + \"" + message + "\" in Korean in 200 characters or less with a metaphorical tone. Be sure to review and make suggestions to make sure your tone isn't awkward. ##Do not remind me what I asked you for## 그리고 대답은 친구처럼 반말로 대답해줘"));
//
//        Body body = new Body("gpt-3.5-turbo", list);
//
//        RequestEntity<Body> httpEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, uri);
//
//        return restTemplate.exchange(httpEntity, String.class).toString();
        return ""
;    }

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