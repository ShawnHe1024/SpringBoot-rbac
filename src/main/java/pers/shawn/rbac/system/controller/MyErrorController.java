package pers.shawn.rbac.system.controller;

import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shawn
 * @create 2020/8/20 10:46
 * @desc 404无法使用handler捕获的异常错误处理
 **/
@RestController
public class MyErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(ERROR_PATH)
    public ResultBean<String> error(){
        return new ResultBean<>(ResultCode.NOT_FOUND);
    }

}
