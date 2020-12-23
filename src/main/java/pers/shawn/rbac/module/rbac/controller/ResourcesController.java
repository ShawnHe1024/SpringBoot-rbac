package pers.shawn.rbac.module.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.service.IResourcesService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shawn
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
    public ResultBean<Object> addResource(
            @Valid Resources resources
    ) {
        resources.setName(resources.getName().trim());
        resources.setUrl(resources.getUrl().trim());
        if (Boolean.TRUE.equals(
                iResourcesService.existResources(resources.getName(), resources.getUrl()))
        ) {
            return new ResultBean<>(ResultCode.REPEAT_DATA);
        }
        resources.setCreateTime(LocalDateTime.now());
        resources.setUpdateTime(LocalDateTime.now());
        boolean b =iResourcesService.save(resources);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("新增资源失败！");
        }
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
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "parentId", value = "父级菜单id"),
            @ApiImplicitParam(name = "type", value = "菜单类型"),
            @ApiImplicitParam(name = "name", value = "资源名称"),
            @ApiImplicitParam(name = "url", value = "资源地址"),
            @ApiImplicitParam(name = "desc", value = "资源描述"),
    })
    @PostMapping("/updResource")
    public ResultBean updResource(
            @RequestParam(name="id")
                    Long id,
            @RequestParam(name="parentId", required = false)
                    Long parentId,
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
        if (parentId == null && type == null && StringUtils.isBlank(name) && StringUtils.isBlank(url) && StringUtils.isBlank(desc)) {
            return new ResultBean<>(ResultCode.MISSING_PARAMETERS);
        }
        Integer count = iResourcesService.count(new LambdaQueryWrapper<Resources>()
                .eq(Resources::getId, id)
        );
        if (count <= 0) {
            return new ResultBean(ResultCode.NOT_FOUND_DATA);
        }
        boolean b = iResourcesService.update(new LambdaUpdateWrapper<Resources>()
                .set(Resources::getName, name)
                .set(Resources::getUrl, url)
                .set(Resources::getParentId, parentId)
                .set(Resources::getType, type)
                .set(Resources::getDescription, desc)
                .set(Resources::getUpdateTime, LocalDateTime.now())
                .eq(Resources::getId, id)
        );
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("修改资源失败！");
        }
    }

    /**
     * 删除资源接口（逻辑删除）
     * @param id
     * @return 处理结果true or false
     */
    @ApiOperation("删除资源")
    @PostMapping("/delResource")
    public Boolean delResource(
            @RequestParam(name="id") Long id
    ) {
        return iResourcesService.removeById(id);
    }

}
