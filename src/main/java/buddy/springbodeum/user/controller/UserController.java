package buddy.springbodeum.user.controller;

import buddy.springbodeum.user.service.KakaoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UserController {

    private final KakaoService kakaoService;

    public UserController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @RequestMapping(value="/kakao/url", method= RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
        System.out.println(kakaoService.getKakaoLogin());
        return "index";
    }

    //백에서 테스트용
    @RequestMapping(value = "/kakao/redirect", method = RequestMethod.GET)
    public void callback(HttpServletRequest request) throws Exception {
        System.out.println(request);

        String remoteAddr = request.getRemoteAddr(); // 클라이언트 IP 주소
        String method = request.getMethod(); // HTTP 요청 메서드 (GET, POST 등)
        String uri = request.getRequestURI(); // 요청 URI
        String queryString = request.getQueryString(); // 쿼리 스트링

        System.out.println("Remote Address: " + remoteAddr);
        System.out.println("HTTP Method: " + method);
        System.out.println("Request URI: " + uri);
        System.out.println("Query String: " + queryString);

    }

    //실제로 인가코드는 프론트에서 전달
    @RequestMapping(value = "/kakao/callback", method = RequestMethod.GET)
    public void kakaoCallback(@RequestBody String code) throws IOException {
        String accessToken = kakaoService.getKakaoAccessToken(code);
    }
}
