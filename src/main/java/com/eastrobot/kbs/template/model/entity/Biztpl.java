package com.eastrobot.kbs.template.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Biztpl extends BaseEntity {

    @TableId(value = "ID", type = IdType.UUID)
    private String id;

    @TableField("NAME")
    private String name;

    @TableField("CATE_ID")
    private String cateId;

    @TableField("HTML_CONTENT")
    private String htmlContent;

    @TableField("JSON_CONTENT")
    private String jsonContent;

    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @TableField("CREATE_USER")
    private String createUser;

    @TableField("MODIFY_DATE")
    private LocalDateTime modifyDate;

    @TableField("MODIFY_USER")
    private String modifyUser;

    @TableField("DELFLAG")
    private Integer delflag;

    @TableField("IS_DRAFT")
    private Integer isDraft;

    @TableField("START_DATE")
    private LocalDateTime startDate;

    @TableField("END_DATE")
    private LocalDateTime endDate;

    @TableField("ATTR_COUNT")
    private Integer attrCount;

    @TableField("CLASS_COUNT")
    private Integer classCount;

    @TableField("STATUS")
    private Integer status;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
