package buddy.springbodeum.user;

import buddy.springbodeum.user.base.BaseResponse;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.dto.MyPageResponseDTO;
import buddy.springbodeum.user.dto.UpdateMyPageRequestDTO;
import buddy.springbodeum.user.dto.UserLoginResponseDTO;
import buddy.springbodeum.user.service.KakaoService;
import buddy.springbodeum.user.service.UserService;
import org.springframework.http.ResponseEntity;
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
        String userName = (String) userInfo.get("nickname");

        User user = userService.kakaoLogin((String) userInfo.get("kakaoId"), (String) userInfo.get("email")); //회원가입

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
        } else{
            System.out.println("access_Token is null");
        }
        return "redirect:/";
    }

    @RequestMapping(value="/{userId}", method= RequestMethod.GET)
    public MyPageResponseDTO myPage(@PathVariable Long userId) {
        return userService.getMyPage(userId);
    }

    @RequestMapping(value="/update/{userId}", method= RequestMethod.PUT)
    public ResponseEntity<String> updateMyPage(@PathVariable Long userId, @RequestBody UpdateMyPageRequestDTO myPageRequestDTO) {
        String nickname = myPageRequestDTO.getNickname();
        String favoriteFluffyName = myPageRequestDTO.getFavoriteFluffyName();
        userService.updateMyPage(userId, nickname, favoriteFluffyName);
        return ResponseEntity.ok("사용자 정보가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
    }
}
