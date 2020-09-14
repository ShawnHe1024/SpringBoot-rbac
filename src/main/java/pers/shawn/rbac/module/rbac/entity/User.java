package pers.shawn.rbac.module.rbac.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author shawn
 * @since 2020-08-17
 */
@ApiModel(description = "用户实体类")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "主键新增时必须为Null")
    private Integer id;

    /**
     * 用户名
     */
    @ExcelProperty(index = 0)
//    @ExcelProperty(index = 4, converter = GAStatusConverter.class)
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 16, message = "用户名长度在3-16之间")
    private String username;

    /**
     * 密码
     */
    @ExcelProperty(index = 1)
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码长度在6-16位之间")
    private String password;

    /**
     * 盐值
     */
    @ApiModelProperty(hidden = true)
    @Null(message = "盐值新增时必须为Null")
    private String salt;

    /**
     * 真实姓名
     */
    @ExcelProperty(index = 2)
    @ApiModelProperty(value = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    @Length(min = 2, max = 16, message = "真实姓名长度在2-16位之间")
    private String realname;

    /**
     * 职位
     */
    @ExcelProperty(index = 3)
    @ApiModelProperty(value = "职位")
    @NotBlank(message = "职位不能为空")
    @Length(min = 2, max = 16, message = "职位长度在2-32位之间")
    private String position;

    /**
     * 手机号
     */
    @ExcelProperty(index = 4)
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "目前只支持11位手机号")
    private String phone;

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
