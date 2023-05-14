package cola.study.advice;

import cola.study.entity.Result;
import cola.study.exception.ColaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: GlobalExceptionHandler
 * @date 2023/5/14 15:53
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ColaException.class)
    public Result<Void> handleColaException(ColaException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage(),null);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage(),null);
    }
}
