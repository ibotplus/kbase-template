package com.eastrobot.kbs.template.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * base view object used for wrap frontend request pojo
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-04-12 17:25
 */
@Getter
@Setter
@Validated
@ApiModel
public class BaseVO implements Serializable {

    @NotEmpty(groups = Update.class)
    @ApiModelProperty("id")
    private String id;


    public interface Create {
    }

    public interface Update {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
