package pers.shawn.rbac.module.rbac.service.impl;

import pers.shawn.rbac.module.rbac.entity.RoleResources;
import pers.shawn.rbac.module.rbac.mapper.RoleResourcesMapper;
import pers.shawn.rbac.module.rbac.service.IRoleResourcesService;
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
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements IRoleResourcesService {

    @Autowired
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    public List<Integer> getRoleResourceList(Integer roleId) {
        return roleResourcesMapper.getRoleResourceList(roleId);
    }

}
