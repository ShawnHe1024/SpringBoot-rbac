package pers.shawn.rbac.module.rbac.mapper;

import pers.shawn.rbac.module.rbac.entity.RoleResources;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
public interface RoleResourcesMapper extends BaseMapper<RoleResources> {

    /**
     * 获取角色拥有的资源列表
     * @param roleId
     * @return
     */
    @Select("SELECT\n" +
            "\tresources.id\n" +
            "FROM\n" +
            "\trole_resources\n" +
            "\tINNER JOIN\n" +
            "\tresources\n" +
            "\tON \n" +
            "\t\trole_resources.resource_id = resources.id\n" +
            "WHERE\n" +
            "\trole_resources.role_id = #{roleId}")
    List<Integer> getRoleResourceList(@Param("roleId") Integer roleId);

}
