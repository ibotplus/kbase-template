package com.eastrobot.kbs.template.model.entity;

import com.eastrobot.kbs.template.exception.ResultCode;
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
 * wrapper for response entity
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-02 12:10
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "响应数据")
public class ResponseEntity<T> implements Serializable {

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
     * response result data
     */
    @ApiModelProperty("响应主体")
    private T data;

    /**
     * common success response entity
     */
    public static <T> ResponseEntity<T> ofSuccess(T data) {
        return new ResponseEntity<T>().setCode(ResultCode.OK.code).setMeaning(ResultCode.OK.meaning).setData(data);
    }

    /**
     * common failure response entity with detail failure message
     */
    public static ResponseEntity ofFailure(String explanation) {
        return new ResponseEntity().setCode(ResultCode.FAILURE.code).setMeaning(ResultCode.FAILURE.code).setExplanation(explanation);
    }

    /**
     * common failure response entity with ResultCode and detail explanation
     */
    public static ResponseEntity ofFailure(ResultCode resultCode) {
        return new ResponseEntity().setCode(resultCode.code).setMeaning(resultCode.meaning);
    }

    /**
     * common failure response entity with ResultCode and detail explanation
     */
    public static ResponseEntity ofFailure(ResultCode resultCode, String explanation) {
        return new ResponseEntity().setCode(resultCode.code).setMeaning(resultCode.meaning).setExplanation(explanation);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}