package cola.study.controller;

import cola.study.entity.Result;
import cola.study.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: AuthController
 * @date 2023/5/14 9:29
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("login")
    public Result<String> login(User user){
        log.info("user: {}",user);
        return Result.ok("登录成功");
    }

    @PostMapping("logout")
    public Result<String> logout(){
        return Result.ok("退出成功");
    }
}
