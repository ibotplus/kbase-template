package com.eastrobot.kbs.template.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
public class UserVO extends BaseVO {

    @NotEmpty(groups = Create.class)
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty(groups = Create.class)
    @ApiModelProperty("密码(前端md5后传入)")
    private String password;

    @NotEmpty(groups = Update.class)
    @ApiModelProperty("关联用户信息id")
    private String userInfoId;

    @ApiModelProperty(value = "-1:删除0:正常")
    private Integer status;

    @ApiModelProperty(value = "主岗id")
    private String mainStationId;

    @ApiModelProperty(value = "访问状态")
    private String visit;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
