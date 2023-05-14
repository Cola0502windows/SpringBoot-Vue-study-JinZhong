package cola.study.controller;

import cola.study.entity.Result;
import cola.study.entity.User;
import cola.study.service.AuthoriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: AuthController
 * @date 2023/5/14 9:29
 */
@Tag(name = "AuthController")
@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Resource
    private AuthoriseService authoriseService;
    private final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private final String USERNAME_REGEX = "^((?!\\\\|\\/|:|\\*|\\?|<|>|\\||'|%|@|#|&|\\$|\\^|&|\\*).){1,8}$";

    @Operation(summary = "login",description = "登录")
    @PostMapping("login")
    public Result<String> login(User user){
        log.info("user: {}",user);
        return Result.ok("登录成功",null);
    }

    @Operation(summary = "logout",description = "退出登录")
    @PostMapping("logout")
    public Result<String> logout(){
        return Result.ok("退出成功",null);
    }

    @Operation(summary = "validate",description = "发送 email 验证码")
    @PostMapping("sendValidateEmail")
    public Result<String> sendValidateEmail(
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email")
            String email,
            @RequestParam("hasAccount")
            Boolean hasAccount,
            HttpSession httpSession){
        if (authoriseService.sendValidateEmail
                (email, httpSession.getId(),hasAccount).getCode() == 200) {
            return Result.ok("邮箱发送成功",null);
        }else {
            return Result.error("邮箱发送失败",null);
        }
    }
    @Operation(summary = "register",description = "注册用户 ")
    @PostMapping("register")
    public Result<String> register(
            @Pattern(regexp = USERNAME_REGEX)
            @Length(min = 2,max = 12)
            @RequestParam("username") String username,
            @Length(min = 6,max = 16)
            @RequestParam("password") String password,
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            @Length(min = 6,max = 6)
            @RequestParam("code") String code,
            HttpSession httpSession
    ){
        if (authoriseService.validateAndRegister
                (username,password,email,code,httpSession.getId()).getCode() == 200){
            return Result.ok("注册成功",null);
        }
        return Result.error();
}

    @PostMapping("/start-reset")
    public Result<String> startReset(
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            @Length(min = 6,max = 6)
            @RequestParam("code") String code,
            HttpSession httpSession
    ){
        if (authoriseService.startReset(email, code,httpSession.getId(),true).getCode() == 200) {
            httpSession.setAttribute("reset-email",email);
            return Result.ok("密码重置成功",null);
        }else {
            return Result.error("密码重置失败",null);
        }
    }
}
