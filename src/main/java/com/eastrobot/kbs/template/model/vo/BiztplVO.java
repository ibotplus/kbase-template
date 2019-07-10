package com.eastrobot.kbs.template.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
* <p>
* 
* </p>
*
* @author yogurt_lei
* @since 2019-07-10
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ApiModel(value="Biztpl对象", description="")
public class BiztplVO extends BaseVO {

    private String name;

    private String cateId;

    private String htmlContent;

    private String jsonContent;

    private LocalDateTime createDate;

    private String createUser;

    private LocalDateTime modifyDate;

    private String modifyUser;

    private Integer delflag;

    private Integer isDraft;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer attrCount;

    private Integer classCount;

    private Integer status;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
