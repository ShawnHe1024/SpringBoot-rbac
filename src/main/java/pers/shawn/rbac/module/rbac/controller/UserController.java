package pers.shawn.rbac.module.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.shawn.rbac.bean.ResultBean;
import pers.shawn.rbac.bean.ResultCode;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.service.IUserService;
import pers.shawn.rbac.util.PasswordUtil;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author shawn
 * @create 2020/8/17 17:18
 * @desc 用户管理相关接口
 **/
@Api(tags = "用户管理")
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 获取用户列表接口，支持按真实姓名搜索
     * @param pageNo
     * @param pageSize
     * @param realname
     * @return 集合分页对象
     */
    @ApiOperation("获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页数", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "realname", value = "真实姓名")
    })
    @GetMapping("/getUserList")
    public IPage<User> getUserList(
            @RequestParam(name="pageNo", defaultValue="1")
                    Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10")
                    Integer pageSize,
            @RequestParam(name="realname", required = false)
                    String realname
    ) {
        IPage<User> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(User::getId, User::getUsername, User::getRealname,
                User::getPhone, User::getPosition, User::getAvatar, User::getCreateTime);
        if (StringUtils.isNotBlank(realname)) {
            queryWrapper.like(User::getRealname, realname);
        }
        page = iUserService.page(page, queryWrapper);
        return page;
    }

    /**
     * 新增用户接口
     * @param user
     * @return 处理结果true or false
     */
    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public ResultBean<Object> addUser(
            @Valid User user
    ) {
        user.setUsername(user.getUsername().trim());
        if (Boolean.TRUE.equals(iUserService.existUser(user.getUsername()))) {
            return new ResultBean<>(ResultCode.USER_ALREADY_REGISTER);
        }
        String password = user.getPassword().trim();
        String salt = PasswordUtil.generateSalt();
        String hashWord = DigestUtils.md5Hex(password+salt);
        user.setSalt(salt);
        user.setPassword(hashWord);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        boolean b = iUserService.save(user);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("新增用户失败！");
        }
    }


    /**
     * 修改用户信息接口
     * @param id
     * @param realname
     * @param position
     * @param phone
     * @return 处理结果true or false
     */
    @ApiOperation("修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "Integer"),
            @ApiImplicitParam(name = "realname", value = "真实姓名"),
            @ApiImplicitParam(name = "position", value = "职位"),
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "avatar", value = "头像"),
    })
    @PostMapping("/updUser")
    public ResultBean<Object> updUser(
            @RequestParam(name="id")
                    Long id,
            @Length(min = 2, max = 16, message = "真实姓名长度在2-16位之间")
            @RequestParam(name="realname", required = false)
                    String realname,
            @RequestParam(name="position", required = false)
            @Length(min = 2, max = 16, message = "职位长度在2-32位之间")
                    String position,
            @RequestParam(name="phone", required = false)
            @Length(min = 11, max = 11, message = "目前只支持11位手机号")
                    String phone,
            @RequestParam(name="avatar", required = false)
                    String avatar
    ) {
        if (StringUtils.isBlank(realname) && StringUtils.isBlank(position) && StringUtils.isBlank(phone) && StringUtils.isBlank(avatar)) {
            return new ResultBean<>(ResultCode.MISSING_PARAMETERS);
        }
        Integer count = iUserService.count(new LambdaQueryWrapper<User>()
                .eq(User::getId, id)
        );
        if (count <= 0) {
            return new ResultBean<>(ResultCode.NO_SUCH_USER);
        }
        User user = new User();
        user.setId(id);
        user.setRealname(realname);
        user.setPosition(position);
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setUpdateTime(LocalDateTime.now());
        boolean b = iUserService.updateById(user);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("修改用户失败！");
        }
    }

    /**
     * 删除用户接口（逻辑删除）
     * @param id
     * @return 处理结果true or false
     */
    @ApiOperation("删除用户")
    @PostMapping("/delUser")
    public ResultBean<Object> delUser(
            @RequestParam(name="id") Long id
    ) {
        boolean b = iUserService.removeById(id);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("删除用户失败！");
        }
    }

    /**
     * 查询用户详情接口
     * @return
     */
    @ApiOperation("用户详情")
    @GetMapping("/getUserInfo")
    public User getUserInfo(
            @RequestParam(name="id") Long id
    ) {
        return iUserService.getOne(new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getRealname,
                        User::getPhone, User::getPosition, User::getAvatar, User::getCreateTime)
                .eq(User::getId, id)
        );
    }

    /**
     * 重新设置用户密码接口
     * @param id
     * @param password
     * @return 处理结果true or false
     */
    @ApiOperation("重置用户密码")
    @PostMapping("/resetPassword")
    public ResultBean<Object> resetPassword(
            @RequestParam(name="id", required = true)
                    Long id,
            @RequestParam(name="password", required = true)
            @Length(min = 6, max = 16, message = "密码长度在6-16位之间")
                    String password
    ) {
        User user = new User();
        user.setId(id);
        String salt = PasswordUtil.generateSalt();
        String hashWord = DigestUtils.md5Hex(password+salt);
        user.setSalt(salt);
        user.setPassword(hashWord);
        boolean b = iUserService.updateById(user);
        if (b) {
            return ResultBean.success();
        } else {
            return ResultBean.failed("重置用户密码失败！");
        }
    }

}
