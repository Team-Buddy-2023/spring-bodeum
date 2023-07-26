package buddy.springbodeum.controller.user;

import buddy.springbodeum.service.user.KakaoService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

//    private final UserService userService;
    private final KakaoService kakaoService;

    public UserController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());

        return "index";
    }


    // 프론트에서 인가코드 받아오는 url
//    @GetMapping("/oauth/token")
//    public User getLogin(@RequestParam("code") String code) {
//
//        // 넘어온 인가 코드를 통해 access_token 발급
//        OauthToken oauthToken = userService.getAccessToken(code);
//
//        //(1)
//        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
//        String User = userService.saveUser(oauthToken.getAccess_token());
//
//        return User;
//    }
}
