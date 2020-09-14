package pers.shawn.rbac.module.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.module.rbac.entity.RoleResources;
import pers.shawn.rbac.module.rbac.service.IRoleResourcesService;
import pers.shawn.rbac.module.rbac.vo.BatchResource;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shawn
 * @create 2020/8/20 14:30
 * @desc 角色资源相关接口
 **/
@Api(tags = "角色资源关系管理")
@RestController
@RequestMapping("/roleResources")
public class RoleResourcesController {

    @Autowired
    private IRoleResourcesService iRoleResourcesService;

    /**
     * 获取角色拥有的资源接口
     * @param roleId
     * @return 角色拥有的所有资源id集合
     */
    @ApiOperation("获取角色拥有的资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer")
    })
    @GetMapping("/getRoleResourceList")
    public List<Integer> getUserRoleList(
            @RequestParam(name="roleId") Integer roleId
    ) {
        return iRoleResourcesService.getRoleResourceList(roleId);
    }

    /**
     * 角色绑定资源接口
     * @param batchResource
     * @return 处理结果true or false
     */
    @ApiOperation("角色绑定资源接口")
    @PostMapping("/addRoleResources")
    public Boolean addRoleResources (
            @RequestBody
                    BatchResource batchResource
    ) {
        List<RoleResources> exist = iRoleResourcesService.list(new LambdaQueryWrapper<RoleResources>()
                .eq(RoleResources::getRoleId, batchResource.getRoleId())
                .in(RoleResources::getResourceId, batchResource.getResourceIds())
        );
        batchResource.getResourceIds().removeAll(
                exist.stream().map(roleResources -> roleResources.getResourceId()).collect(Collectors.toList())
        );
        List<RoleResources> roleResources = new ArrayList<>();
        for (Integer resourceId : batchResource.getResourceIds()) {
            RoleResources roleResource = new RoleResources();
            roleResource.setRoleId(batchResource.getRoleId());
            roleResource.setResourceId(resourceId);
            roleResource.setCreateTime(LocalDateTime.now());
            roleResources.add(roleResource);
        }
        return iRoleResourcesService.saveBatch(roleResources);
    }

    /**
     * 角色解除资源接口
     * @param batchResource
     * @return 处理结果true or false
     */
    @ApiOperation("角色解除资源接口")
    @PostMapping("/delRoleResources")
    public boolean delRoleResources(
            @RequestBody
                    BatchResource batchResource
    ) {
        return iRoleResourcesService.remove(new LambdaQueryWrapper<RoleResources>()
                .eq(RoleResources::getRoleId, batchResource.getRoleId())
                .in(RoleResources::getResourceId, batchResource.getResourceIds())
        );
    }

}
