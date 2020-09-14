package pers.shawn.rbac.bean;

/**
 * @author hexiang
 * @create 2018-05-02 下午2:44
 * @desc 接口统一返回对象
 **/
public class ResultBean<T> {

    private Integer code;

    private String message;

    private T data;

    public static ResultBean<Object> success() {
        return new ResultBean<>(ResultCode.SUCCESS);
    }

    public static ResultBean<Object> success(Object data) {
        return new ResultBean<>(ResultCode.SUCCESS, data);
    }

    public ResultBean(ResultCode resultCode, String message, T data) {
        this.code = resultCode.getCode();
        this.message = message;
        this.data = data;
    }

    public ResultBean(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    public ResultBean(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
