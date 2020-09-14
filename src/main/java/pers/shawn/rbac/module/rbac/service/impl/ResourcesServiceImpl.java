package pers.shawn.rbac.module.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.mapper.ResourcesMapper;
import pers.shawn.rbac.module.rbac.service.IResourcesService;
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
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements IResourcesService {

    @Autowired
    private ResourcesMapper resourcesMapper;

    @Override
    public Boolean existResources(String name, String url) {
        Integer i = resourcesMapper.selectCount(new LambdaQueryWrapper<Resources>()
                .eq(Resources::getName, name)
                .or()
                .eq(Resources::getUrl, url)
        );
        return i>0;
    }

}
