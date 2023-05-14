package cola.study.service.impl;

import cola.study.entity.User;
import cola.study.mapper.UserMapper;
import cola.study.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: UserServiceImpl
 * @date 2023/5/13 17:12
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User findByID(Long id) {
        return userMapper.findByID(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
