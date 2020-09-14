package pers.shawn.rbac.system.interceptor;

import pers.shawn.rbac.common.Constants;
import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import pers.shawn.rbac.util.ThreadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shawn
 * @create 2020/8/18 17:19
 * @desc 权限拦截器
 **/
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String token = ThreadUtil.getToken();
        Boolean b = redisTemplate.opsForHash().hasKey(Constants.REDIS_HASH_PREFIX+token, url);
        if (b) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(objectMapper.writeValueAsString(new ResultBean<>(ResultCode.ACCESS_DENIED)));
        response.getWriter().flush();
        return false;
    }
}
