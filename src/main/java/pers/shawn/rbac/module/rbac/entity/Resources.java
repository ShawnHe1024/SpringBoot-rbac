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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@ApiModel(description = "资源实体类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Resources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    @Null(message = "主键新增时必须为Null")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    @NotNull(message = "父级id不能为空")
    private Integer parentId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "资源名称")
    @NotBlank(message = "资源名称不能为空")
    @Length(min = 2, max = 16, message = "资源名称长度在2-16之间")
    private String name;

    /**
     * 类型（0为页面资源，1为接口资源）
     */
    @ApiModelProperty(value = "类型（0为页面资源，1为接口资源）")
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 资源地址
     */
    @ApiModelProperty(value = "资源地址")
    @NotBlank(message = "资源地址不能为空")
    @Length(min = 2, max = 255, message = "资源地址长度在2-255之间")
    private String url;

    /**
     * 备注
     */
    @ApiModelProperty(value = "资源描述")
    @Length(max = 255, message = "资源描述最大为255位")
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
