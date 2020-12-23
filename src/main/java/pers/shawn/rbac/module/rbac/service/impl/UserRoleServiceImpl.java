package pers.shawn.rbac.module.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.User;
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
    public List<UserRole> getUserRoleList(Long userId) {
        return userRoleMapper.getUserRoleList(userId);
    }

    @Override
    public IPage<User> getRoleUserList(Page<User> page, Long roleId) {
        return userRoleMapper.getRoleUserList(page, roleId);
    }

    @Override
    public IPage<User> getUserByNotRoleId(Page<User> page, Long roleId) {
        return userRoleMapper.getUserByNotRoleId(page, roleId);
    }

    @Override
    public List<Resources> getUserRoleResources(Long userId) {
        return userRoleMapper.getUserRoleResources(userId);
    }

}
