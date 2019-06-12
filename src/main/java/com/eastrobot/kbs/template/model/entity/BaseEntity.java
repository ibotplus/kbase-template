package com.eastrobot.kbs.template.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
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
public class BaseEntity implements Serializable {
    @ApiModelProperty("id")
    protected String id;

    @ApiModelProperty("创建时间")
    protected Date createDate;

    @ApiModelProperty("修改时间")
    protected Date modifyDate;

    @ApiModelProperty("创建人")
    protected String createUser;

    @ApiModelProperty("修改人")
    protected String modifyUser;

    /**
     * Logic to delete is 1, else is 0
     */
    @ApiModelProperty("逻辑删除标志")
    protected Integer delFlag;
}
