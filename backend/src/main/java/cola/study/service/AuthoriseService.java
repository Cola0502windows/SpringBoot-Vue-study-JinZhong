package cola.study.service;

import cola.study.entity.Result;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: AuthoriseService
 * @date 2023/5/14 9:33
 */
public interface AuthoriseService extends UserDetailsService {

    Result<String> sendValidateEmail(String email, String sessionId, boolean hasAccount);

    Result<String> validateAndRegister(String username, String password, String email, String code,String sessionId);
}
