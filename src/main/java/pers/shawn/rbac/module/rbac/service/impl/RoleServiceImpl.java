package pers.shawn.rbac.module.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.module.rbac.entity.Role;
import pers.shawn.rbac.module.rbac.mapper.RoleMapper;
import pers.shawn.rbac.module.rbac.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Boolean existRole(String name) {
        Integer i = roleMapper.selectCount(new LambdaQueryWrapper<Role>()
                .eq(Role::getName, name)
        );
        return i>0;
    }

}
