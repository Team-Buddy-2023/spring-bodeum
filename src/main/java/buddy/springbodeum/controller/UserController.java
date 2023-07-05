package buddy.springbodeum.controller;

import buddy.springbodeum.domain.User;
import buddy.springbodeum.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public boolean signUp(@RequestBody Map<String, String> paramMap) {

        System.out.println(paramMap);

        String userId = paramMap.get("userId");
        String password = paramMap.get("password");

        //닉네임 자동생성

        String nickname = "닉네임";

        User newUser = new User(userId, password, nickname);
        return userService.signUp(newUser);
    }
}
