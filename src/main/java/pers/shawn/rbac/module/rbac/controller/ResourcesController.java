package pers.shawn.rbac.module.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.service.IResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shawn
 * @create 2020/8/20 10:26
 * @desc 资源管理相关接口
 **/
@Api(tags = "资源管理")
@Validated
@RestController
@RequestMapping("/resources")
public class ResourcesController {

    @Autowired
    private IResourcesService iResourcesService;

    /**
     * 获取资源列表接口
     * @return 资源列表对象
     */
    @ApiOperation("获取资源列表")
    @GetMapping("/getResourcesList")
    public List<Resources> getResourcesList() {
        return iResourcesService.list();
    }

    /**
     * 新增资源接口
     * @param resources
     * @return 处理结果true or false
     */
    @ApiOperation("新增资源")
    @PostMapping("/addResource")
    public Boolean addResource(
            @Valid Resources resources
    ) {
        resources.setName(resources.getName().trim());
        resources.setUrl(resources.getUrl().trim());
        if (Boolean.TRUE.equals(
                iResourcesService.existResources(resources.getName(), resources.getUrl()))
        ) {
            return false;
        }
        resources.setCreateTime(LocalDateTime.now());
        resources.setUpdateTime(LocalDateTime.now());
        return iResourcesService.save(resources);
    }

    /**
     * 修改资源信息接口
     * @param id
     * @param name
     * @param desc
     * @return 处理结果true or false
     */
    @ApiOperation("修改资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "角色名称"),
            @ApiImplicitParam(name = "desc", value = "角色描述"),
    })
    @PostMapping("/updResource")
    public Boolean updRole(
            @RequestParam(name="id")
                    Integer id,
            @RequestParam(name="parentId", required = false)
                    Integer parentId,
            @RequestParam(name="type", required = false)
                    Integer type,
            @Length(min = 2, max = 16, message = "资源名称长度在2-16之间")
            @RequestParam(name="name", required = false)
                    String name,
            @Length(min = 2, max = 255, message = "资源地址长度在2-255之间")
            @RequestParam(name="url", required = false)
                    String url,
            @RequestParam(name="desc", required = false)
            @Length(max = 255, message = "资源描述最大为255位")
                    String desc
    ) {
        Integer count = iResourcesService.count(new LambdaQueryWrapper<Resources>()
                .eq(Resources::getId, id)
        );
        if (count <= 0) {
            return false;
        }
        Resources resources = new Resources();
        resources.setId(id);
        resources.setName(name);
        resources.setUrl(url);
        resources.setParentId(parentId);
        resources.setType(type);
        resources.setDescription(desc);
        return iResourcesService.updateById(resources);
    }

    /**
     * 删除资源接口（逻辑删除）
     * @param id
     * @return 处理结果true or false
     */
    @ApiOperation("删除资源")
    @PostMapping("/delResource")
    public Boolean delResource(
            @RequestParam(name="id") Integer id
    ) {
        return iResourcesService.removeById(id);
    }

}
