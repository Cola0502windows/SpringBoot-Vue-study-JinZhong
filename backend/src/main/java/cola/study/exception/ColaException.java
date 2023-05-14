package cola.study.exception;

import cola.study.enums.ErrorCode;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: ColaException
 * @date 2023/5/14 15:51
 */
public class ColaException extends RuntimeException{
    private int code = 500;

    public ColaException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ColaException() {
    }

    public ColaException(String message) {
        super(message);
    }

    public ColaException(String message, Throwable cause) {
        super(message, cause);
    }

    public ColaException(Throwable cause) {
        super(cause);
    }

    public ColaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }
}
