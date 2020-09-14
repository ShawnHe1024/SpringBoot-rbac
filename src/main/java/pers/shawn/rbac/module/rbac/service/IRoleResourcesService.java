package pers.shawn.rbac.module.rbac.service;

import pers.shawn.rbac.module.rbac.entity.RoleResources;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface IRoleResourcesService extends IService<RoleResources> {

    List<Integer> getRoleResourceList(Integer roleId);
}
