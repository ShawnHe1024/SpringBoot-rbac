package pers.shawn.rbac.module.rbac.service;

import pers.shawn.rbac.module.rbac.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface IRoleService extends IService<Role> {

    Boolean existRole(String name);
}
