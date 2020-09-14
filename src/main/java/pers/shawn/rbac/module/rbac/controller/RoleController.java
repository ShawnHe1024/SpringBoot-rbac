package pers.shawn.rbac.module.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.shawn.rbac.module.rbac.entity.Role;
import pers.shawn.rbac.module.rbac.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author shawn
 * @create 2020/8/19 14:03
 * @desc 角色管理相关接口
 **/
@Api(tags = "角色管理")
@Validated
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;

    /**
     * 获取角色列表接口，支持按角色名称搜索
     * @param pageNo
     * @param pageSize
     * @param name
     * @return 集合分页对象
     */
    @ApiOperation("获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "角色名称")
    })
    @GetMapping("/getRoleList")
    public IPage<Role> getRoleList(
            @RequestParam(name="pageNo", defaultValue="1")
                    Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10")
                    Integer pageSize,
            @RequestParam(name="name", required = false)
                    String name
    ) {
        IPage<Role> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Role> queryWrapper = null;
        if (StringUtils.isNotBlank(name)) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name", name);
        }
        page = iRoleService.page(page, queryWrapper);
        return page;
    }

    /**
     * 新增角色接口
     * @param role
     * @return 处理结果true or false
     */
    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    public Boolean addRole(
            @Valid Role role
    ) {
        role.setName(role.getName().trim());
        if (Boolean.TRUE.equals(iRoleService.existRole(role.getName()))) {
            return false;
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        return iRoleService.save(role);
    }

    /**
     * 修改角色信息接口
     * @param id
     * @param name
     * @param desc
     * @return 处理结果true or false
     */
    @ApiOperation("修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "角色名称"),
            @ApiImplicitParam(name = "desc", value = "角色描述"),
    })
    @PostMapping("/updRole")
    public Boolean updRole(
            @RequestParam(name="id")
                    Integer id,
            @Length(min = 2, max = 16, message = "角色名称长度在2-16位之间")
            @RequestParam(name="name", required = false)
                    String name,
            @RequestParam(name="desc", required = false)
            @Length(max = 255, message = "角色描述最大为255位")
                    String desc
    ) {
        Integer count = iRoleService.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getId, id)
        );
        if (count <= 0) {
            return false;
        }
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setDescription(desc);
        return iRoleService.updateById(role);
    }

    /**
     * 删除角色接口（逻辑删除）
     * @param id
     * @return 处理结果true or false
     */
    @ApiOperation("删除角色")
    @PostMapping("/delRole")
    public Boolean delRole(
            @RequestParam(name="id") Integer id
    ) {
        return iRoleService.removeById(id);
    }



}
