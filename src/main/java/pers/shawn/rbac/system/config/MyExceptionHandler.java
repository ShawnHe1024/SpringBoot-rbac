package pers.shawn.rbac.system.config;

import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shawn
 * @create 2020/8/17 17:32
 * @desc 统一异常处理类
 **/
@RestControllerAdvice
public class MyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * valid校验异常处理
     * @param e
     * @return 异常信息bean
     */
    @ExceptionHandler({BindException.class})
    public ResultBean<String> validError(BindException e) {
        StringBuilder sb = new StringBuilder("参数错误：[");
        List<ObjectError> list = e.getAllErrors();
        for (ObjectError item : list) {
            sb.append(item.getDefaultMessage()).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');

        String msg = sb.toString();
        return new ResultBean<>(ResultCode.ILLEGAL_PARAMETERS, msg, null);
    }

    /**
     * validated校验异常处理
     * @param e
     * @return 异常信息bean
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResultBean<String> validatedError(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder("参数错误：[");
        sb.append(
                e.getConstraintViolations().stream()
                        .map(cv -> cv == null ?"":cv.getMessage())
                        .collect(Collectors.joining(", "))
        );
        sb.append(']');
        String msg = sb.toString();
        return new ResultBean<>(ResultCode.ILLEGAL_PARAMETERS, msg, null);
    }

    /**
     * 缺少参数异常
     * @param e
     * @return 异常信息bean
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResultBean<String> missingParam(MissingServletRequestParameterException e) {
        return new ResultBean<>(ResultCode.MISSING_PARAMETERS);
    }

    /**
     * 其他异常
     * @param exception
     * @return 异常信息bean
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResultBean<String> serverError(Exception exception) {
        logger.error(ResultCode.INTERNAL_SERVER_ERROR.getMsg(), exception);
        return new ResultBean<>(ResultCode.INTERNAL_SERVER_ERROR);
    }

}
