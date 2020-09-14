package pers.shawn.rbac.module.rbac.service;

import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

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

    List<UserRole> getUserRoleList(@Param("userId") Integer userId);

    List<UserRole> getRoleUserList(Integer roleId);

    List<Resources> getUserRoleResources(Integer userId, Integer resourceType);
}
