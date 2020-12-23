package pers.shawn.rbac.bean;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author shawn
 * @create 2020/8/17 17:45
 * @desc 状态返回码枚举
 **/
public enum ResultCode {

    // 操作成功
    SUCCESS(0, ""),

    /**
     * 服务器异常
     */
    //服务器内部错误
    INTERNAL_SERVER_ERROR(10000, ""),

    //非法请求
    ILLEGAL_REQUESTS(20000, ""),

    //非法参数
    ILLEGAL_PARAMETERS(20001, ""),

    //缺失参数
    MISSING_PARAMETERS(20002, ""),

    NOT_FOUND(20003, ""),

    //请求文件大小超出限制
    UPLOAD_FILE_TOOLARGE(20004, ""),

    /**
     * 权限异常
     */

    //无访问权限
    ACCESS_DENIED(30000, ""),

    //用户名/密码错误
    USERINFO_WRONG(30001, ""),

    //账号被锁定
//    USER_LOCKED(30002, ""),

    //认证过期
    CREDENTIALS_EXPIRED(30003, ""),

//    SMS_CODE_WRONG(30004, ""),

    /**
     * 业务异常
     */

    //业务处理失败
    BUSINESS_FAILED(40000, ""),
    //用户已注册
    USER_ALREADY_REGISTER(40001, ""),
    //没有该用户
    NO_SUCH_USER(40002, ""),
    //Excel导出失败
    EXPORT_EXCEL_FAILED(40003, ""),
    //Excel导出无数据
    EXPORT_EXCEL_EMPTY(40004, ""),
    //没有查到该数据
    NOT_FOUND_DATA(40005, ""),
    //重复数据
    REPEAT_DATA(40006, ""),
    ;

    private int code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * 默认采用服务器的locale信息
     * 可以通过在header里添加Accept-Language：en-US/zh-CN来更改请求的locale
     * @return
     */
    public String getMsg() {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("resultMSG", locale);
        return bundle.getString(String.valueOf(code));
    }

    @Override
    public String toString() {
        return code + " : " + msg;
    }

}
