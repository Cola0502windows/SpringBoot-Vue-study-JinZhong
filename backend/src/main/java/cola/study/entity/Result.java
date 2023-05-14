package cola.study.entity;

import lombok.Data;

/**
 * @author Cola0502-JinZhong
 * @version 1.0
 * @description: Result 代表向前端返回的数据
 * @date 2023/5/13 17:17
 */

@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private Result() {
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> ok(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result<Void> ok() {
        Result<Void> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        return result;
    }

    public static Result<Void> error() {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage("操作失败");
        return result;
    }

    public static Result<Void> error(String message) {
        Result<Void> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static Result<Void> error(Integer code, String message) {
        Result<Void> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


}

