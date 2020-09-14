package pers.shawn.rbac.module.rbac.service;

import pers.shawn.rbac.module.rbac.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface IUserService extends IService<User> {

    /**
     * 查询用户是否存在
     * @param username
     * @return 存在返回true，否则返回false
     */
    Boolean existUser(String username);
}
