package pers.shawn.rbac.module.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.mapper.UserMapper;
import pers.shawn.rbac.module.rbac.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean existUser(String username) {
        Integer i = userMapper.selectCount(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        return i>0;
    }

}
