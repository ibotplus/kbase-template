package com.eastrobot.kbs.template.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>
 *
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User对象")
@Entity
@Table(name = "DEV_USER")
public class User extends BaseEntity {

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_INFO_ID")
    private String userInfoId;

    // @TableLogic
    // @ApiModelProperty(value = "-1:删除0:正常")
    // @TableField("STATUS")
    // private Integer status;

    @Column(name = "MAIN_STATION_ID")
    private String mainStationId;

    @Column(name = "VISIT")
    private String visit;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
