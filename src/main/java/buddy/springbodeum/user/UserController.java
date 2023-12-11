package buddy.springbodeum.user;

import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.service.KakaoService;
import buddy.springbodeum.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;

    public UserController(KakaoService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
    }

//    @RequestMapping(value="/kakao/url", method= RequestMethod.GET)
//    public String login(Model model) {
//        model.addAttribute("kakaoUrl", kakaoService.getKakaoLogin());
//        System.out.println(kakaoService.getKakaoLogin());
//        return "index";
//    }
//
//    //백에서 테스트용
//    @RequestMapping(value = "/kakao/redirect", method = RequestMethod.GET)
//    public void callback(HttpServletRequest request) throws Exception {
//        System.out.println(request);
//
//        String remoteAddr = request.getRemoteAddr(); // 클라이언트 IP 주소
//        String method = request.getMethod(); // HTTP 요청 메서드 (GET, POST 등)
//        String uri = request.getRequestURI(); // 요청 URI
//        String queryString = request.getQueryString(); // 쿼리 스트링
//
//        System.out.println("Remote Address: " + remoteAddr);
//        System.out.println("HTTP Method: " + method);
//        System.out.println("Request URI: " + uri);
//        System.out.println("Query String: " + queryString);
//
//    }

    @RequestMapping(value="/kakao/login", method= RequestMethod.GET)
    public String kakaoLogin() {
        System.out.println("test");
        String kakaoLoginURL = kakaoService.getKakaoLogin();
        System.out.println(kakaoLoginURL);
        return kakaoLoginURL;
    }

    @RequestMapping(value = "/oauth/callback/kakao", method = RequestMethod.GET)
    public User kakaoCallback(@RequestParam("code") String code) {
        System.out.println("test2");
        // Kakao로부터 받은 인가코드 처리
        // 이 코드에서는 간단히 받은 코드를 출력하도록 했습니다.
        System.out.println("인가코드: " + code);

        String accessToken = kakaoService.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
        System.out.println("닉네임 : " + userInfo.get("nickname"));
        System.out.println("이메일 : " + userInfo.get("email"));
        return userService.kakaoLogin(userInfo.get("nickname"), userInfo.get("email"));
    }

    @RequestMapping(value="/kakao/logout")
    public String logout(HttpSession session) {
        String access_Token = (String)session.getAttribute("access_Token");

        if(access_Token != null && !"".equals(access_Token)){
            kakaoService.kakaoLogout(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("userId");
        }else{
            System.out.println("access_Token is null");
        }
        return "redirect:/";
    }
}

