package buddy.springbodeum.user;

import buddy.springbodeum.chat.ChatRepository;
import buddy.springbodeum.chat.data.Chat;
import buddy.springbodeum.user.base.BaseResponse;
import buddy.springbodeum.user.data.User;
import buddy.springbodeum.user.dto.MyPageResponseDTO;
import buddy.springbodeum.user.dto.UpdateMyPageRequestDTO;
import buddy.springbodeum.user.dto.UserLoginResponseDTO;
import buddy.springbodeum.user.service.KakaoService;
import buddy.springbodeum.user.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public UserController(KakaoService kakaoService, UserService userService, ChatRepository chatRepository, UserRepository userRepository) {
        this.kakaoService = kakaoService;
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/check")
    public String check() {
        return "OK";
    }

    @RequestMapping(value="/kakao/login", method= RequestMethod.GET)
    public String kakaoLogin() {
        String kakaoLoginURL = kakaoService.getKakaoLogin();
        System.out.println(kakaoLoginURL);
        return kakaoLoginURL;
    }

    @RequestMapping(value = "/oauth/callback/kakao", method = RequestMethod.GET)
    public BaseResponse<UserLoginResponseDTO> kakaoCallback(@RequestParam("code") String code) {
        System.out.println("인가코드: " + code);

        String accessToken = kakaoService.getKakaoAccessToken(code);
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);

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

        if (nickname != null && userService.validateDuplicateNickname(nickname)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용 중인 닉네임입니다.");
        }

        userService.updateMyPage(userId, nickname, favoriteFluffyName);
        return ResponseEntity.ok("사용자 정보가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("사용자가 성공적으로 삭제되었습니다.");
        } catch (DataIntegrityViolationException e) {
            // 외래 키 제약 조건 위배로 인한 예외 처리
            deleteChatsByUserId(userId);
            userService.deleteUser(userId);
            return ResponseEntity.ok("사용자와 관련된 모든 채팅과 함께 사용자가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("사용자 삭제 중 오류가 발생했습니다.");
        }
    }

    private void deleteChatsByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            List<Chat> chats = chatRepository.findByUser(optionalUser.get());
            chatRepository.deleteAll(chats);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다. userId: " + userId);
        }
    }

}
