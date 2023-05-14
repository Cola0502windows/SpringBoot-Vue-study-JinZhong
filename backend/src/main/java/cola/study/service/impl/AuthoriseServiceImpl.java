package cola.study.service.impl;

import cola.study.entity.User;
import cola.study.mapper.UserMapper;
import cola.study.service.AuthoriseService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: AuthoriseServiceImpl
 * @date 2023/5/14 9:34
 */
@Service
public class AuthoriseServiceImpl implements AuthoriseService {
    @Resource
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null) throw new UsernameNotFoundException("用户名不能为空");
        // 查询 user
        User user = userMapper.findByUserName(username);

        if (user == null) throw new UsernameNotFoundException("用户名或密码错误");

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(" ")
                .build();
    }
}
