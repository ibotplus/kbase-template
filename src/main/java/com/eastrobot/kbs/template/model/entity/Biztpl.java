package com.eastrobot.kbs.template.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
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
@ApiModel(value = "Biztpl对象", description = "")
public class Biztpl extends BaseEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "CATE_ID")
    private String cateId;

    @Column(name = "HTML_CONTENT")
    private String htmlContent;

    @Column(name = "JSON_CONTENT")
    private String jsonContent;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @Column(name = "MODIFY_USER")
    private String modifyUser;

    @Column(name = "DELFLAG")
    private Integer delflag;

    @Column(name = "IS_DRAFT")
    private Integer isDraft;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "ATTR_COUNT")
    private Integer attrCount;

    @Column(name = "CLASS_COUNT")
    private Integer classCount;

    @Column(name = "STATUS")
    private Integer status;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
