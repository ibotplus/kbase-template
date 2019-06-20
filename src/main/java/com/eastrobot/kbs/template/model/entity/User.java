package com.eastrobot.kbs.template.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* <p>
* 
* </p>
*
* @author yogurt_lei
* @since 2019-06-19
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
@Builder
@TableName("DEV_USER")
public class User {

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("USERNAME")
    private String username;

    @TableField("PASSWORD")
    private String password;

    @TableField("USER_INFO_ID")
    private String userInfoId;

    @TableLogic
    @ApiModelProperty(value = "-1:删除0:正常")
    @TableField("STATUS")
    private Integer status;

    @TableField("MAIN_STATION_ID")
    private String mainStationId;

    @TableField("VISIT")
    private String visit;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
