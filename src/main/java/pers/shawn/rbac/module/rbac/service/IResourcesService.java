package pers.shawn.rbac.module.rbac.service;

import pers.shawn.rbac.module.rbac.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface IResourcesService extends IService<Resources> {

    /**
     * 根据资源名称和地址判断是否已存在该资源
     * @param name
     * @param url
     * @return 判断结果true or false
     */
    Boolean existResources(String name, String url);
}
