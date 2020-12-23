package pers.shawn.rbac.module.rbac.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@ApiModel(description = "角色实体类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    @Null(message = "主键新增时必须为Null")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 16, message = "角色名称长度在2-16之间")
    private String name;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色编码不能为空")
    @Length(min = 2, max = 32, message = "角色编码长度在2-32之间")
    private String roleCode;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @Length(max = 255, message = "角色描述最大为255位")
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private LocalDateTime updateTime;

    /**
     * 删除标识
     */
    @ApiModelProperty(hidden = true)
    @Null(message = "标识新增时必须为Null")
    @TableLogic
    private Boolean delFlag;


}
