package pers.shawn.rbac.module.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.module.rbac.entity.UserRole;
import pers.shawn.rbac.module.rbac.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author shawn
 * @create 2020/8/19 14:03
 * @desc 用户角色相关接口
 **/
@Api(tags = "用户角色关系管理")
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private IUserRoleService iUserRoleService;

    /**
     * 获取用户拥有的角色接口
     * @param userId
     * @return 用户拥有的所有角色集合
     */
    @ApiOperation("获取用户拥有的角色接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer")
    })
    @GetMapping("/getUserRoleList")
    public List<UserRole> getUserRoleList(
            @RequestParam(name="userId") Integer userId
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
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer")
    })
    @GetMapping("/getRoleUserList")
    public List<UserRole> getRoleUserList(Integer roleId) {
        return iUserRoleService.getRoleUserList(roleId);
    }

    /**
     * 用户绑定角色接口
     * @param userId
     * @param roleId
     * @return 处理结果true or false
     */
    @ApiOperation("用户绑定角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer")
    })
    @PostMapping("/addUserRole")
    public Boolean addUserRole(
            @RequestParam(name="userId")
                    Integer userId,
            @RequestParam(name="roleId")
                    Integer roleId
    ) {
        Integer count = iUserRoleService.count(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId)
        );
        if (count > 0) {
            return true;
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(LocalDateTime.now());
        return iUserRoleService.save(userRole);
    }

    /**
     * 用户解绑角色接口
     * @param userId
     * @param roleId
     * @return 处理结果true or false
     */
    @ApiOperation("用户解绑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer"),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "Integer")
    })
    @PostMapping("/delUserRole")
    public Boolean delUserRole(
            @RequestParam(name="userId")
                    Integer userId,
            @RequestParam(name="roleId")
                    Integer roleId
    ) {
        LambdaQueryWrapper wrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, roleId);
        Integer count = iUserRoleService.count(wrapper);
        if (count == 0) {
            return true;
        }
        return iUserRoleService.remove(wrapper);
    }

}
