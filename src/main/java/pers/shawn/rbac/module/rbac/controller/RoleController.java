package pers.shawn.rbac.module.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import pers.shawn.rbac.module.rbac.entity.Role;
import pers.shawn.rbac.module.rbac.entity.RoleResources;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.entity.UserRole;
import pers.shawn.rbac.module.rbac.service.IRoleResourcesService;
import pers.shawn.rbac.module.rbac.service.IRoleService;
import pers.shawn.rbac.module.rbac.service.IUserRoleService;
import pers.shawn.rbac.module.rbac.vo.BatchResource;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shawn
 * @desc 角色管理相关接口
 **/
@Api(tags = "角色管理")
@Validated
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRoleResourcesService iRoleResourcesService;
    @Autowired
    private IUserRoleService iUserRoleService;

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
    public ResultBean<Object> addRole(
            @Valid Role role
    ) {
        role.setName(role.getName().trim());
        if (Boolean.TRUE.equals(iRoleService.existRole(role.getName()))) {
            return new ResultBean<>(ResultCode.REPEAT_DATA);
        }
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        boolean b = iRoleService.save(role);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("新增角色失败！");
        }
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
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "角色名称"),
            @ApiImplicitParam(name = "desc", value = "角色描述"),
    })
    @PostMapping("/updRole")
    public ResultBean updRole(
            @RequestParam(name="id")
                    Long id,
            @Length(min = 2, max = 16, message = "角色名称长度在2-16位之间")
            @RequestParam(name="name", required = false)
                    String name,
            @Length(min = 2, max = 32, message = "角色编码长度在2-32之间")
            @RequestParam(name="roleCode", required = false)
                    String roleCode,
            @RequestParam(name="desc", required = false)
            @Length(max = 255, message = "角色描述最大为255位")
                    String desc
    ) {
        if (StringUtils.isBlank(name) && StringUtils.isBlank(roleCode) && StringUtils.isBlank(desc)) {
            return new ResultBean<>(ResultCode.MISSING_PARAMETERS);
        }
        Integer count = iRoleService.count(new LambdaQueryWrapper<Role>()
                .eq(Role::getId, id)
        );
        if (count <= 0) {
            return new ResultBean(ResultCode.REPEAT_DATA);
        }
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setRoleCode(roleCode);
        role.setDescription(desc);
        role.setUpdateTime(LocalDateTime.now());
        boolean b = iRoleService.updateById(role);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("修改角色失败！");
        }
    }

    /**
     * 删除角色接口（逻辑删除）
     * @param id
     * @return 处理结果true or false
     */
    @ApiOperation("删除角色")
    @PostMapping("/delRole")
    public ResultBean<Object> delRole(
            @RequestParam(name="id") Long id
    ) {
        boolean b = iUserRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getRoleId, id)
        );
        if (b) {
            b = iRoleService.removeById(id);
            if (b) {
                return ResultBean.success();
            }
        }
        return ResultBean.failed("删除角色失败！");
    }

    /**
     * 获取角色拥有的资源接口
     * @param roleId
     * @return 角色拥有的所有资源id集合
     */
    @ApiOperation("获取角色拥有的资源接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long")
    })
    @GetMapping("/getRoleResourceList")
    public List<Long> getRoleResourceList(
            @RequestParam(name="roleId") Long roleId
    ) {
        return iRoleResourcesService.getRoleResourceList(roleId);
    }

    /**
     * 修改角色绑定资源接口
     * @param batchResource
     * @return 处理结果true or false
     */
    @ApiOperation("修改角色绑定资源接口")
    @PostMapping("/updRoleResources")
    public ResultBean<Object> addRoleResources (
            @RequestBody
                    BatchResource batchResource
    ) {
        if (batchResource.getRoleId() == null || batchResource.getObjectIds() == null || batchResource.getObjectIds().size() <= 0) {
            return new ResultBean<>(ResultCode.ILLEGAL_PARAMETERS);
        }
        //先删后加
        iRoleResourcesService.remove(new LambdaQueryWrapper<RoleResources>()
                .eq(RoleResources::getRoleId, batchResource.getRoleId())
        );
        List<RoleResources> roleResources = new ArrayList<>();
        for (Long resourceId : batchResource.getObjectIds()) {
            RoleResources roleResource = new RoleResources();
            roleResource.setRoleId(batchResource.getRoleId());
            roleResource.setResourceId(resourceId);
            roleResource.setCreateTime(LocalDateTime.now());
            roleResources.add(roleResource);
        }
        boolean b = iRoleResourcesService.saveBatch(roleResources);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("处理失败！");
        }
    }

    /**
     * 获取用户拥有的角色接口
     * @param userId
     * @return 用户拥有的所有角色集合
     */
    @ApiOperation("获取用户拥有的角色接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long")
    })
    @GetMapping("/getUserRoleList")
    public List<UserRole> getUserRoleList(
            @RequestParam(name="userId") Long userId
    ) {
        return iUserRoleService.getUserRoleList(userId);
    }

    /**
     * 获取角色绑定的用户接口
     * @param roleId
     * @return 角色拥有的所有用户集合
     */
    @ApiOperation("获取角色绑定的用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long")
    })
    @GetMapping("/getRoleUserList")
    public IPage<User> getRoleUserList(
            @RequestParam(name="pageNo", defaultValue="1")
                    Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10")
                    Integer pageSize,
            @RequestParam(name="roleId", required = true)
                    Long roleId
    ) {
        Page<User> page = new Page<>(pageNo, pageSize);
        return iUserRoleService.getRoleUserList(page, roleId);
    }

    /**
     * 获取角色未绑定的用户接口
     * @param roleId
     * @return 角色未拥有的所有用户集合
     */
    @ApiOperation("获取角色未绑定的用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long")
    })
    @GetMapping("/getUserByNotRoleId")
    public IPage<User> getUserByNotRoleId(
            @RequestParam(name="pageNo", defaultValue="1")
                    Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10")
                    Integer pageSize,
            @RequestParam(name="realname", required = false)
                    String realname,
            @RequestParam(name="roleId", required = true)
                    Long roleId
    ) {
        Page<User> page = new Page<>(pageNo, pageSize);
        return iUserRoleService.getUserByNotRoleId(page, roleId);
    }

    /**
     * 用户绑定角色接口
     * @param userId
     * @param roleId
     * @return 处理结果true or false
     */
    @ApiOperation("用户绑定角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long")
    })
    @PostMapping("/addUserRole")
    public ResultBean<Object> addUserRole(
            @RequestParam(name="userId")
                    Long userId,
            @RequestParam(name="roleId")
                    Long roleId
    ) {
        Integer count = iUserRoleService.count(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId)
        );
        if (count > 0) {
            return new ResultBean<>(ResultCode.REPEAT_DATA);
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(LocalDateTime.now());
        boolean b = iUserRoleService.save(userRole);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("处理失败！");
        }
    }

    /**
     * 用户解绑角色接口
     * @param userId
     * @param roleId
     * @return 处理结果true or false
     */
    @ApiOperation("用户解绑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Long")
    })
    @PostMapping("/delUserRole")
    public ResultBean<Object> delUserRole(
            @RequestParam(name="userId")
                    Long userId,
            @RequestParam(name="roleId")
                    Long roleId
    ) {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId);
        Integer count = iUserRoleService.count(wrapper);
        if (count == 0) {
            return new ResultBean<>(ResultCode.REPEAT_DATA);
        }
        boolean b = iUserRoleService.remove(wrapper);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("处理失败！");
        }
    }

    /**
     * 角色批量绑定用户
     * @param batchResource
     * @return 处理结果true or false
     */
    @ApiOperation("角色批量绑定用户")
    @PostMapping("/addRoleUsers")
    public ResultBean<Object> addRoleUsers (
            @RequestBody
                    BatchResource batchResource
    ) {
        if (batchResource.getRoleId() == null || batchResource.getObjectIds() == null || batchResource.getObjectIds().size() <= 0) {
            return new ResultBean<>(ResultCode.ILLEGAL_PARAMETERS);
        }
        List<UserRole> exist = iUserRoleService.list(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getRoleId, batchResource.getRoleId())
                .in(UserRole::getUserId, batchResource.getObjectIds())
        );
        batchResource.getObjectIds().removeAll(
                exist.stream().map(roleResources -> roleResources.getUserId()).collect(Collectors.toList())
        );
        List<UserRole> userRoles = new ArrayList<>();
        for (Long resourceId : batchResource.getObjectIds()) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(batchResource.getRoleId());
            userRole.setUserId(resourceId);
            userRole.setCreateTime(LocalDateTime.now());
            userRoles.add(userRole);
        }
        if (userRoles.size() > 0) {
            boolean b = iUserRoleService.saveBatch(userRoles);
            if (b) {
                return ResultBean.success();
            } else {
                return ResultBean.failed();
            }
        }
        return ResultBean.success();
    }

    /**
     * 角色批量解绑用户
     * @param batchResource
     * @return 处理结果true or false
     */
    @ApiOperation("角色批量解绑用户")
    @PostMapping("/delRoleUsers")
    public ResultBean<Object> delRoleUsers(
            @RequestBody
                    BatchResource batchResource
    ) {
        if (batchResource.getRoleId() == null || batchResource.getObjectIds() == null || batchResource.getObjectIds().size() <= 0) {
            return new ResultBean<>(ResultCode.ILLEGAL_PARAMETERS);
        }
        boolean b = iUserRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getRoleId, batchResource.getRoleId())
                .in(UserRole::getUserId, batchResource.getObjectIds())
        );
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("处理失败！");
        }
    }

}
