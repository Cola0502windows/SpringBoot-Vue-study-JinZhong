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

    @Operation(summary = "login")
    @PostMapping("login")
    public Result<String> login(User user){
        log.info("user: {}",user);
        return Result.ok("登录成功");
    }

    @Operation(summary = "logout")
    @PostMapping("logout")
    public Result<String> logout(){
        return Result.ok("退出成功");
    }

    @Operation(summary = "validate")
    @PostMapping("validate-email")
    public Result<String> sendValidateEmail(
            @Pattern(regexp = EMAIL_REGEX)
            @RequestParam("email") String email,
            HttpSession httpSession){
        if (authoriseService.sendValidateEmail
                (email, httpSession.getId(),false).getCode() == 200) {
            return Result.ok(null);
        }else {
            return Result.error();
        }
    }
    @Operation(summary = "register")
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
            return Result.ok(null);
        }
        return Result.error();
}
}
