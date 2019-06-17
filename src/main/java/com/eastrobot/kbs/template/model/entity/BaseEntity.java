package com.eastrobot.kbs.template.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * base entity used for wrap datasource pojo
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-27 9:04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("base entity")
@ToString
@EqualsAndHashCode
public class BaseEntity implements Serializable {

    @ApiModelProperty("id")
    protected String id;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    protected Date createDate;

    @TableField(value = "MODIFY_DATE", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改时间")
    protected Date modifyDate;

    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    protected String createUser;

    @TableField(value = "MODIFY_USER", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改人")
    protected String modifyUser;

    /**
     * Logic to delete is 1, else is 0
     */
    @TableLogic
    @ApiModelProperty("逻辑删除标志")
    protected Integer delFlag;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
