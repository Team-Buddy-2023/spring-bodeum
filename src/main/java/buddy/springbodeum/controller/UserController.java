package buddy.springbodeum.controller;

import buddy.springbodeum.domain.User;
import buddy.springbodeum.repository.UserRepository;
import buddy.springbodeum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //회원가입
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public String signUp(@RequestBody Map<String, String> paramMap) {

        System.out.println(paramMap);

        String userId = paramMap.get("userId");
        String password = paramMap.get("password");

        //닉네임 자동생성
        String nickname = userService.randomNickname();

        User newUser = new User(userId, password, nickname);
        userService.signUp(newUser);
        return "signup";
    }

    //아이디 중복 확인
    @RequestMapping(value = "/validateDuplicateUserId", method = RequestMethod.POST)
    public boolean validateDuplicateUserId(@RequestBody String userId) {
        if(userRepository.existsByUserId(userId)) {
            System.out.println("아이디 중복 테스트");
            return false;   //userId가 중복이면 false return
        }
        else return true;
    }
}
