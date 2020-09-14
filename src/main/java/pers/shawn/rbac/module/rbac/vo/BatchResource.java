package pers.shawn.rbac.module.rbac.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author shawn
 * @create 2020/8/21 10:41
 * @desc
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BatchResource {

    private Integer roleId;
    private List<Integer> resourceIds;

}
