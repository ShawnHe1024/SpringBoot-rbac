package pers.shawn.rbac.bean;


/**
 * @author shawn
 * @create 2020-08-17 16:46
 * @desc   自定义异常类
 **/
public class NativeException extends Exception {

    private final ResultCode resultCode;

    public NativeException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public NativeException(String msg){
        super(msg);
        this.resultCode = ResultCode.INTERNAL_SERVER_ERROR;
    }

    public NativeException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;

    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
