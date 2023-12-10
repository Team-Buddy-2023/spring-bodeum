package buddy.springbodeum.chat.service;

import buddy.springbodeum.character.CharacterService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class GPTService {

    private final CharacterService characterService;

    public GPTService(CharacterService characterService) {
        this.characterService = characterService;
    }

    public String createAnswer(String message, Long characterId) {
        String description = characterService.getCharacter(characterId).getDescription();
        String content = description + message + " 그리고 대답은 친구처럼 반말로 대답해줘";
        return send(content);
    }

    @Value("${chatgpt.api-key}")
    private String key;
    public String send(String content) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.openai.com/v1/chat/completions")
                .build()
                .encode()
                .toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + key);
        ArrayList<Message> list = new ArrayList<>();
        list.add(new Message("system",content));
        Body body = new Body("gpt-3.5-turbo", list);
        RequestEntity<Body> httpEntity = new RequestEntity<>(body, httpHeaders, HttpMethod.POST, uri);

        return restTemplate.exchange(httpEntity, String.class).toString();
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