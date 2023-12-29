package buddy.springbodeum.user;

import buddy.springbodeum.user.data.BaseResponse;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.data.UserLoginResponseDTO;
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
    public BaseResponse<UserLoginResponseDTO> kakaoCallback(@RequestParam("code") String code) {
        System.out.println("인가코드: " + code);

        String accessToken = kakaoService.getAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);

        User user = userService.kakaoLogin((String) userInfo.get("kakaoId"), (String) userInfo.get("nickname"), (String) userInfo.get("email")); //회원가입

        UserLoginResponseDTO userLoginResponseDTO = kakaoService.kakaoLogin(user);
        return new BaseResponse<>(userLoginResponseDTO.getStatus(), "요청 성공했습니다.", userLoginResponseDTO);
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
