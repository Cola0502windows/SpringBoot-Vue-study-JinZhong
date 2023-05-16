package cola.study.service.impl;

import cola.study.entity.Result;
import cola.study.entity.User;
import cola.study.enums.ErrorCode;
import cola.study.exception.ColaException;
import cola.study.mapper.UserMapper;
import cola.study.service.AuthoriseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: AuthoriseServiceImpl
 * @date 2023/5/14 9:34
 */
@Service
@Slf4j
public class AuthoriseServiceImpl implements AuthoriseService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromMail;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    @Override
    public Result<String> sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;

        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            Long expire = Optional.ofNullable(stringRedisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                throw new ColaException(ErrorCode.EMAIL_SEND_60S);
            }
        }
        User user = userMapper.findByUserEmail(email);

        if (hasAccount && user == null) throw new ColaException(ErrorCode.EMAIL_IS_NEW);
        if (!hasAccount && user != null) throw new ColaException(ErrorCode.EMAIL_WAS_REGISTERED);

        Random random = new Random();
        int code = random.nextInt(899999) + 100000;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("您的验证码为：");
        simpleMailMessage.setText("验证码为：" + code);
        try {
            mailSender.send(simpleMailMessage);
            stringRedisTemplate.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);
            return Result.ok(null);
        } catch (MailException e) {
            if (log.isDebugEnabled()) {
                log.debug("邮件发送失败");
            }
            throw new ColaException(ErrorCode.EMAIL_SEND_FAILURE);
        }
        /*
          1. 先生成对应的验证码
          2. 把邮箱和对应的验证码放到 Redis 中（过期时间为 3 min P.S. 是要剩余时间低于 2 min 就可以再次发送一次邮件，然后重复此流程）
          3. 将验证码发送到指定邮箱
          4. 如果发送失败，将 Redis 中刚刚插入的数据清除
          5. 用户在注册时，再从 Redis 中取出相对应的键值对，对比是否一致
         */
    }

    @Override
    public Result<String> validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":false";

        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String redis_code = stringRedisTemplate.opsForValue().get(key);
            if (redis_code == null) {
                throw new ColaException(ErrorCode.EMAIL_MUST_NEED);
            } else if (redis_code.equals(code)) {
                password = encoder.encode(password);
                stringRedisTemplate.delete(key);
                if (userMapper.saveUser(username, password, email) > 0) {
                    return Result.ok(null);
                } else {
                    throw new ColaException(ErrorCode.REGISTER_FAILURE);
                }
            }
        }
        return Result.error();
    }

    @Override
    public Result<String> startReset(String email, String code, String sessionId, boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email + ":true";

        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            String redis_code = stringRedisTemplate.opsForValue().get(key);
            if (redis_code == null) {
                throw new ColaException(ErrorCode.EMAIL_MUST_NEED);
            } else if (redis_code.equals(code)) {

                stringRedisTemplate.delete(key);
                return Result.ok(null);
            }
        }
        return Result.error();
    }

    @Override
    public Result<String> resetPwd(String password, String email) {
        password = encoder.encode(password);
        if (userMapper.resetPwd(password, email) > 0){
            return Result.ok("密码重置成功", null);
        }else {
            return Result.error("密码重置失败", null);
        }
    }
}
