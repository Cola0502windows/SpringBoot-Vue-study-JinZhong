package cola.study.enums;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: ErrorCode
 * @date 2023/5/13 17:16
 */
public enum ErrorCode {
    /**
     * 40101: token不能为空
     */
    TOKEN_IS_EMPTY(40101, "token 不能为空"),
    /**
     * 40102: token已过期
     */
    TOKEN_IS_EXPIRED(40102, "token 已过期"),
    /**
     * 40103: token不合法
     */
    TOKEN_IS_ILLEGAL(40103, "token 不合法"),

    /**
     * 40201: 邮箱未被注册
     */
    EMAIL_IS_NEW(40201,"邮箱未被注册"),
    /**
     * 40202: 邮箱已经被注册
     */
    EMAIL_WAS_REGISTERED(40202,"邮箱已经被注册"),

    /**
     * 40203： 发送频繁,请 60s 后再试
     */
    EMAIL_SEND_60S(40203,"发送频繁,请 60s 后再试"),

    /**
     * 40204: 请先获取验证码
     */
    EMAIL_MUST_NEED(40204,"请先获取验证码"),
    /**
     * 40205: 邮箱发送失败
     */
    EMAIL_SEND_FAILURE(402205,"邮箱发送失败"),
    /**
     * 40301: 注册失败
     */
    REGISTER_FAILURE(40301,"注册失败")
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
