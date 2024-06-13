package buddy.springbodeum.user.service;

import buddy.springbodeum.user.UserRepository;
import buddy.springbodeum.user.data.*;
import buddy.springbodeum.user.dto.UserLoginResponseDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoService {

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    @Value("${kakao.admin.key}")
    private String SERVICE_APP_ADMIN_KEY;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public KakaoService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    public String getKakaoAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // Http Response Body 객체 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); //카카오 공식문서 기준 authorization_code 로 고정
        params.add("client_id", KAKAO_CLIENT_ID); // 카카오 Dev 앱 REST API 키
        params.add("redirect_uri", KAKAO_REDIRECT_URL); // 카카오 Dev redirect uri
        params.add("code", code); // 프론트에서 인가 코드 요청시 받은 인가 코드값
        params.add("client_secret", KAKAO_CLIENT_SECRET); // 카카오 Dev 카카오 로그인 Client Secret

        // 헤더와 바디 합치기 위해 Http Entity 객체 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 카카오로부터 Access token 받아오기
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token", // "https://kauth.kakao.com/oauth/token"
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // JSON Parsing
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(accessTokenResponse.getBody()).getAsJsonObject();

//        String refresh_token = jsonObject.get("refresh_token").getAsString();

        return jsonObject.get("access_token").getAsString();
    }

    public HashMap<String, Object> getUserInfo(String accessToken) {
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().getAsJsonPrimitive("id").getAsString();
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = null;
            JsonElement emailElement = kakao_account.getAsJsonObject().get("email");
            if (emailElement != null) {
                email = emailElement.getAsString();
            }

            userInfo.put("kakaoId", id);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public UserLoginResponseDTO kakaoLogin(User user) {
        Long userId = user.getUserId();
        String token = jwtTokenProvider.createToken(userId);
        return new UserLoginResponseDTO(HttpStatus.OK, token, userId, user.getNickname(), user.getImageURL());
    }

    public void kakaoLogoutUnlink(Long kakaoId, String reqURL) {
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "KakaoAK " + SERVICE_APP_ADMIN_KEY);
            conn.setDoOutput(true);  // OutputStream 사용 가능 설정

            // 요청 본문 데이터 설정
            String urlParameters = "target_id_type=user_id&target_id=" + kakaoId;
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            // 데이터 전송
            try (BufferedOutputStream bos = new BufferedOutputStream(conn.getOutputStream())) {
                bos.write(postData);
                bos.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());
        } catch (IOException e) {
            System.err.println("IOException occurred while logging out: " + e.getMessage());
            e.printStackTrace();
        }
    }
}