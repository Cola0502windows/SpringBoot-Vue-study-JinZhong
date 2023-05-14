package cola.study.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: User
 * @date 2023/5/13 17:10
 */


@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别 0-男 1-女
     */
    private Integer gender;
    /**
     * 密码
     */
    private String password;

    /**
     * 用于密码加密的盐值
     */
    private String salt;

    /**
     * 身份证号
     */
    private String idCard;


    /**
     * 邮箱
     */
    private String email;


    /**
     * 电话
     */
    private String phone;


    private LocalDateTime birthday;

    /**
     *  年龄
     */
    private Integer age;

    /**
     * 状态 1-正常 2-禁用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLogin;


}

