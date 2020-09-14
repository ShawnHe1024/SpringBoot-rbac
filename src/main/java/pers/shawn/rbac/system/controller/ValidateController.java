package pers.shawn.rbac.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.service.IResourcesService;
import pers.shawn.rbac.module.rbac.service.IRoleService;
import pers.shawn.rbac.module.rbac.service.IUserService;

import javax.validation.constraints.NotBlank;

/**
 * @author shawn
 * @create 2020/8/19 11:35
 * @desc
 **/
@Api(tags = "数据校验接口")
@Validated
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IResourcesService iResourcesService;

    /**
     * 校验接口，用于新增时前端校验用户名是否可用
     * @param username
     * @return 校验结果，true为可用，false为不可用
     */
    @ApiOperation("查询用户名是否可用")
    @ApiImplicitParam(name = "username", value = "用户名")
    @PostMapping("/checkUsername")
    public Boolean checkUsername(
            @RequestParam(name="username")
            @NotBlank(message = "用户名不能为空")
            @Length(min = 3, max = 16, message = "用户名长度在3-16之间")
                    String username
    ) {
        return !iUserService.existUser(username.trim());
    }

    /**
     * 校验接口，用于新增时前端校验角色名是否可用
     * @param name
     * @return 校验结果，true为可用，false为不可用
     */
    @ApiOperation("查询角色名是否可用")
    @ApiImplicitParam(name = "name", value = "角色名")
    @PostMapping("/checkRoleName")
    public Boolean checkRoleName(
            @RequestParam(name="name")
            @NotBlank(message = "角色名不能为空")
            @Length(min = 2, max = 16, message = "角色名称长度在2-16之间")
                    String name
    ) {
        return !iRoleService.existRole(name.trim());
    }

    /**
     * 校验接口，用于新增时前端校验资源名称是否可用
     * @param name
     * @return 校验结果，true为可用，false为不可用
     */
    @ApiOperation("查询资源名称是否可用")
    @ApiImplicitParam(name = "name", value = "资源名称")
    @PostMapping("/checkResourceName")
    public Boolean checkResourceName(
            @RequestParam(name="name")
            @NotBlank(message = "资源名称不能为空")
            @Length(min = 2, max = 16, message = "资源名称长度在2-16之间")
                    String name
    ) {
        Integer i = iResourcesService.count(new LambdaQueryWrapper<Resources>()
                .eq(Resources::getName, name.trim())
        );
        return i == 0;
    }

    /**
     * 校验接口，用于新增时前端校验资源地址是否可用
     * @param url
     * @return 校验结果，true为可用，false为不可用
     */
    @ApiOperation("查询资源地址是否可用")
    @ApiImplicitParam(name = "url", value = "资源地址")
    @PostMapping("/checkResourceURL")
    public Boolean checkResourceURL(
            @RequestParam(name="url")
            @NotBlank(message = "资源地址不能为空")
            @Length(min = 2, max = 255, message = "资源地址长度在2-255之间")
                    String url
    ) {
        Integer i = iResourcesService.count(new LambdaQueryWrapper<Resources>()
                .eq(Resources::getUrl, url.trim())
        );
        return i == 0;
    }

}
