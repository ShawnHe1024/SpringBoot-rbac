package pers.shawn.rbac.module.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface IUserRoleService extends IService<UserRole> {

    List<UserRole> getUserRoleList(Long userId);

    IPage<User> getRoleUserList(Page<User> page, Long roleId);

    IPage<User> getUserByNotRoleId(Page<User> page, Long roleId);

    List<Resources> getUserRoleResources(Long userId);
}
