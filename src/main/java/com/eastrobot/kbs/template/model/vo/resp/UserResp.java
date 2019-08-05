package com.eastrobot.kbs.template.model.vo.resp;

import com.eastrobot.kbs.template.model.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel(value="UserResp", description="")
public class UserResp extends BaseVO {
    @ApiModelProperty("创建时间")
    protected LocalDateTime createDate;

    @ApiModelProperty("修改时间")
    protected LocalDateTime modifyDate;

    @ApiModelProperty("用户名")
    private String username;
}
