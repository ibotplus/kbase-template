package com.eastrobot.kbs.template.util.pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * NOTE: when pageNum equals zero, meaning full page, determine by config pagehelper.page-size-zero=true
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-04-10 18:23
 */

@Data
@ApiModel("分页请求")
@Accessors(chain = true)
@Validated
public class PageInfoRequest implements Serializable {

    @Min(1)
    @ApiModelProperty(value = "分页参数: 起始页为1", example = "1")
    private int pageNum;

    @Min(5)
    @ApiModelProperty(value = "分页参数: 页大小", example = "5")
    private int pageSize;

    // TODO by Yogurt_lei on 2019-04-14 13:42 : OrderBy,Where Fields

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
