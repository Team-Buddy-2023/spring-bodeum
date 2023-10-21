//package buddy.springbodeum.user.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class KakaoService {
//
//    @Value("${kakao.client.id}")
//    private String KAKAO_CLIENT_ID;
//
//    @Value("${kakao.client.secret}")
//    private String KAKAO_CLIENT_SECRET;
//
//    @Value("${kakao.redirect.url}")
//    private String KAKAO_REDIRECT_URL;
//
//    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
//
//
//    public String getKakaoLogin() {
//        return KAKAO_AUTH_URI + "/oauth/authorize"
//                + "?client_id=" + KAKAO_CLIENT_ID
//                + "&redirect_uri=" + KAKAO_REDIRECT_URL
//                + "&response_type=code";
//    }
//
//    public String getKakaoAccessToken(String code) throws IOException {
//        String accessToken = "";
//        String refreshToken = "";
//        String reqURL = "https://kauth.kakao.com/oauth/token";
//
////        try {
////            URL url = new URL(reqURL);
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////
////        }
//
//        return accessToken;
//    }
//}
