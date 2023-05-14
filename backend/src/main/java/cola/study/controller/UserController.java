package cola.study.controller;

import cola.study.entity.Result;
import cola.study.entity.User;
import cola.study.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: UserController
 * @date 2023/5/13 17:23
 */
@Tag(name = "UserController")
@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    UserService userService;

    @Operation(summary = "findByID/{id}",description = "根据 ID 查找")
    @Parameter(name = "id",description = "User ID",in = ParameterIn.PATH)
    @GetMapping("findByID/{id}")
    public Result<User> findByID(@PathVariable("id") Long id){
        User user = userService.findByID(id);
        return Result.ok(user);
    }

    @Operation(summary = "findAll",description = "查找全部")
    @GetMapping("findAll")
    public Result<List<User>> findAll(){
        List<User> userList = userService.findAll();
        return Result.ok(userList);
    }

}
