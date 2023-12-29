package buddy.springbodeum.chat.service;

import buddy.springbodeum.character.Character;
import buddy.springbodeum.character.CharacterService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;


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
        Character character = characterService.getCharacter(characterId);

        if (character == null) {
        }

        String description = character.getDescription();
        System.out.println(description);

        if (description == null) {
            return "캐릭터 설명이 없습니다.";
        }

        String content = description + message + " 그리고 대답은 친구처럼 반말로 대답해줘";

        String openAIResponse = send(content);
        String jsonOnly = openAIResponse.substring(openAIResponse.indexOf("{"));
        return extractAssistantResponse(jsonOnly);
    }

    private String extractAssistantResponse(String openAIResponse) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열을 JSON 트리로 파싱합니다.
            JsonNode rootNode = objectMapper.readTree(openAIResponse);

            // "choices" 배열을 얻습니다.
            JsonNode choicesNode = rootNode.get("choices");

            // "choices" 배열이 존재하고 배열의 크기가 1 이상인지 확인합니다.
            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                // "choices" 배열에서 첫 번째 선택지를 얻습니다.
                JsonNode firstChoice = choicesNode.get(0);

                // "message" 객체에서 "content" 값을 가져옵니다.
                JsonNode contentNode = firstChoice.path("message").path("content");

                // "content" 값을 문자열로 반환합니다.
                return contentNode.asText();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리를 위해 예외를 출력합니다.
        }

        // 추출에 실패한 경우 null 또는 적절한 기본값을 반환합니다.
        return null;
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

        org.springframework.http.HttpHeaders httpHeaders = new HttpHeaders();
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