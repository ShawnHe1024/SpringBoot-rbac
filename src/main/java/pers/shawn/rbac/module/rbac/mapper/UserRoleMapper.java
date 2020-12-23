package pers.shawn.rbac.module.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.shawn.rbac.module.rbac.entity.Resources;
import pers.shawn.rbac.module.rbac.entity.User;
import pers.shawn.rbac.module.rbac.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 获取用户拥有的角色列表
     * @param userId
     * @return 用户拥有的所有角色集合
     */
    @Select("SELECT\n" +
            "\tuser_role.user_id, \n" +
            "\tuser_role.role_id, \n" +
            "\trole.`name` AS roleName\n" +
            "FROM\n" +
            "\tuser_role\n" +
            "\tINNER JOIN\n" +
            "\trole ON role.id = user_role.role_id\n" +
            "WHERE\n" +
            "\tuser_role.user_id = #{userId} AND\n" +
            "\trole.del_flag = 0")
    List<UserRole> getUserRoleList(@Param("userId") Long userId);

    /**
     * 获取角色绑定的用户列表
     * @param roleId
     * @return 角色拥有的所有用户集合
     */
    @Select("SELECT\n" +
            "\t`user`.id,\n" +
            "\t`user`.position,\n" +
            "\t`user`.avatar,\n" +
            "\t`user`.username,\n" +
            "\t`user`.`realname` AS realname \n" +
            "FROM\n" +
            "\tuser_role\n" +
            "\tINNER JOIN `user` ON `user`.id = user_role.user_id \n" +
            "WHERE\n" +
            "\tuser_role.role_id = #{roleId} AND\n" +
            "\tuser.del_flag = 0")
    IPage<User> getRoleUserList(Page<User> page, @Param("roleId") Long roleId);

    /**
     * 获取角色绑定的用户列表
     * @param roleId
     * @return 角色拥有的所有用户集合
     */
    @Select("SELECT\n" +
            "\tid,\n" +
            "\trealname,\n" +
            "\tposition \n" +
            "FROM\n" +
            "\t`user` \n" +
            "WHERE\n" +
            "\tid NOT IN (\n" +
            "\tSELECT\n" +
            "\t\tuser_id \n" +
            "\tFROM\n" +
            "\t\tuser_role \n" +
            "WHERE\n" +
            "\trole_id = #{roleId})")
    IPage<User> getUserByNotRoleId(Page<User> page, @Param("roleId") Long roleId);

    /**
     * 获取用户拥有的资源列表
     * @param userId
     * @return 用户拥有的所有资源集合
     */
    @Select("SELECT\n" +
            "\tresources.id, \n" +
            "\tresources.parent_id, \n" +
            "\tresources.`name`, \n" +
            "\tresources.type, \n" +
            "\tresources.url\n" +
            "FROM\n" +
            "\tuser_role\n" +
            "\tINNER JOIN\n" +
            "\trole_resources\n" +
            "\tON \n" +
            "\t\tuser_role.role_id = role_resources.role_id\n" +
            "\tINNER JOIN\n" +
            "\tresources\n" +
            "\tON \n" +
            "\t\trole_resources.resource_id = resources.id\n" +
            "WHERE\n" +
            "\tuser_role.user_id = #{userId} AND\n" +
            "\tresources.del_flag = 0")
    List<Resources> getUserRoleResources(@Param("userId") Long userId);

}
