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
    }

    //실제로 인가코드는 프론트에서 전달
    @RequestMapping(value = "/kakao/callback", method = RequestMethod.GET)
    public void kakaoCallback(@RequestBody String code) throws IOException {
        String accessToken = kakaoService.getKakaoAccessToken(code);


    }
}
