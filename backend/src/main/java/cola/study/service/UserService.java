package cola.study.service;

import cola.study.entity.User;

import java.util.List;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: UserService
 * @date 2023/5/13 17:12
 */
public interface UserService {
    User findByID(Long id);

    List<User> findAll();
}
