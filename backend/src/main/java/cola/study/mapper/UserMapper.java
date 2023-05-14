package cola.study.mapper;

import cola.study.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: UserMapper
 * @date 2023/5/13 17:18
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    User findByID(Long id);
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUserName(String username);
    @Select("SELECT * FROM sys_user")
    List<User> findAll();


}
