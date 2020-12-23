package pers.shawn.rbac.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.common.Constants;
import pers.shawn.rbac.bean.LoginUser;
import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.service.IUserRoleService;
import pers.shawn.rbac.module.rbac.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author shawn
 * @create 2020/8/18 15:44
 * @desc 登录注册等开放接口
 **/
@Api(tags = "登录等一级接口")
@Validated
@RestController
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserRoleService iUserRoleService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录接口
     * @param username
     * @param password
     * @return 登录用户信息
     */
    @ApiOperation("登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
    })
    @PostMapping("/login")
    public ResultBean<Object> login(
            @RequestParam(name="username", required = false)
            @NotBlank
                    String username,
            @RequestParam(name="password", required = false)
            @NotBlank
                    String password
    ) {
        User user = iUserService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        if (user == null) {
            return new ResultBean<>(ResultCode.NO_SUCH_USER);
        }
        String hashWord = DigestUtils.md5Hex(password+user.getSalt());
        if (hashWord.equals(user.getPassword())) {
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(user, loginUser);
            String token = UUID.randomUUID().toString().replace("-", "");
            loginUser.setToken(token);
            //Todo:接口权限部分待修改
//            List<Resources> resources =  iUserRoleService.getUserRoleResources(loginUser.getId(), Constants.RESOURCE_TYPE_API);

//            if (resources.size() > 0) {
//                redisTemplate.multi();
//                redisTemplate.opsForHash().putAll(Constants.REDIS_HASH_PREFIX+token,
//                        resources.stream().distinct().collect(
//                                Collectors.toMap(Resources::getUrl, r -> r)
//                        )
//                );
//                redisTemplate.expire(Constants.REDIS_HASH_PREFIX+token, Constants.TOKEN_EXPIRE_TIME);
//                redisTemplate.opsForValue().set(Constants.REDIS_STR_PREFIX+token, loginUser, Constants.TOKEN_EXPIRE_TIME);
//                try {
//                    redisTemplate.exec();
//                } catch (Exception e) {
//                    logger.error("redis事务提交失败", e);
//                    return new ResultBean<>(ResultCode.BUSINESS_FAILED);
//                }
//                return ResultBean.success(loginUser);
//            }
            return new ResultBean<>(ResultCode.ACCESS_DENIED);
        }
        return new ResultBean<>(ResultCode.USERINFO_WRONG);
    }

}
