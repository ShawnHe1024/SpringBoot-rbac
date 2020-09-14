package pers.shawn.rbac.system.controller;

import pers.shawn.rbac.common.Constants;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.service.IUserRoleService;
import pers.shawn.rbac.util.ThreadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shawn
 * @create 2020/8/20 12:07
 * @desc
 **/
@Api(tags = "用户登录后就可以访问的接口")
@Validated
@RestController
@RequestMapping("/permission")
public class PermissonController {

    @Autowired
    private IUserRoleService iUserRoleService;

    @ApiOperation("获取用户拥有的资源列表")
    @GetMapping("/getUserResources")
    public List<Resources> getUserResources() {
        Integer userId = ThreadUtil.getUserHolder().getId();
        return iUserRoleService.getUserRoleResources(userId, Constants.RESOURCE_TYPE_PAGE);
    }

}
