package com.eastrobot.kbs.template.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * (Optional) base data transform object used for connect frontend request pojo(vo) and to dao entity(po)
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-04-12 17:25
 */
@Getter
@Setter
public class BaseDTO implements Serializable {

    private String id;

    private String value;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
