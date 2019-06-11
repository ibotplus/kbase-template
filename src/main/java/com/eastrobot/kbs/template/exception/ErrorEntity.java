package com.eastrobot.kbs.template.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * wrapper for error customize response entity
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-02 12:10
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "错误响应实体")
public class ErrorEntity implements Serializable {
    /**
     * response result code, not http response code
     */
    @ApiModelProperty("响应码")
    private String code = "";

    /**
     * response result code meaning
     */
    @ApiModelProperty("响应码含义")
    private String meaning = "";

    /**
     * response result explanation, usually used to describe exception detail
     */
    @ApiModelProperty("响应码解释，通常用于描述异常细节")
    private String explanation = "";

    /**
     * common failure response entity with detail failure message
     */
    public static ErrorEntity of(String explanation) {
        return new ErrorEntity().setCode(ErrorCode.FAILURE.code).setMeaning(ErrorCode.FAILURE.meaning).setExplanation(explanation);
    }

    /**
     * common failure response entity with detail failure message and detail failure code
     */
    public static ErrorEntity of(String code, String explanation) {
        return new ErrorEntity().setCode(code).setExplanation(explanation);
    }

    /**
     * common failure response entity with ResultCode and detail explanation
     */
    public static ErrorEntity of(ErrorCode errorCode, String explanation) {
        return new ErrorEntity().setCode(errorCode.code).setMeaning(errorCode.meaning).setExplanation(explanation);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
