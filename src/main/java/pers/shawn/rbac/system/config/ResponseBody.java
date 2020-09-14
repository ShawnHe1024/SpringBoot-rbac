package pers.shawn.rbac.system.config;

import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author shawn
 * @create 2020-08-17 16:46
 * @desc 统一返回体封装
 **/
@ControllerAdvice
public class ResponseBody implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (o != null && o.getClass().isAssignableFrom(ResultBean.class)) {
            return o;
        }

        String path = serverHttpRequest.getURI().toString();
        if (!path.contains("actuator") && !path.contains("swagger") && !path.contains("v3")) {
            return new ResultBean<>(ResultCode.SUCCESS, o);
        }

        return o;
    }

}
