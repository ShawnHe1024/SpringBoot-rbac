package pers.shawn.rbac.module.rbac.service.impl;

import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.UserRole;
import pers.shawn.rbac.module.rbac.mapper.UserRoleMapper;
import pers.shawn.rbac.module.rbac.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> getUserRoleList(Integer userId) {
        return userRoleMapper.getUserRoleList(userId);
    }

    @Override
    public List<UserRole> getRoleUserList(Integer roleId) {
        return userRoleMapper.getRoleUserList(roleId);
    }

    @Override
    public List<Resources> getUserRoleResources(Integer userId, Integer resourceType) {
        return userRoleMapper.getUserRoleResources(userId, resourceType);
    }

}
