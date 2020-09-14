package pers.shawn.rbac.system.interceptor;

import pers.shawn.rbac.common.Constants;
import pers.shawn.rbac.bean.LoginUser;
import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import pers.shawn.rbac.util.ThreadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shawn
 * @create 2020/8/18 16:24
 * @desc 登录拦截器
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        if (StringUtils.isBlank(auth)) {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().append(objectMapper.writeValueAsString(new ResultBean<>(ResultCode.ACCESS_DENIED)));
            response.getWriter().flush();
            return false;
        }
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(Constants.REDIS_STR_PREFIX+auth);
        if (loginUser != null) {
            ThreadUtil.setToken(auth);
            ThreadUtil.setUserHolder(loginUser);
            redisTemplate.expire(Constants.REDIS_HASH_PREFIX+auth, Constants.TOKEN_EXPIRE_TIME);
            redisTemplate.expire(Constants.REDIS_STR_PREFIX+auth, Constants.TOKEN_EXPIRE_TIME);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(objectMapper.writeValueAsString(new ResultBean<>(ResultCode.CREDENTIALS_EXPIRED)));
        response.getWriter().flush();
        return false;
    }
}
