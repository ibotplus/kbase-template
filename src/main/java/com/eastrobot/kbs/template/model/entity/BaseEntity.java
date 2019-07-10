package com.eastrobot.kbs.template.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.eastrobot.kbs.template.util.EnvironmentUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * base entity used for wrap datasource pojo
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-27 9:04
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel("base entity")
@MappedSuperclass
@Slf4j
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(updatable = false)
    @TableId(value = "ID", type = IdType.UUID)
    @ApiModelProperty("id")
    protected String id;

    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    @Column(updatable = false)
    @ApiModelProperty("创建时间")
    protected LocalDateTime createDate;

    @TableField(value = "MODIFY_DATE", fill = FieldFill.UPDATE)
    @Column(name = "MODIFY_DATE")
    @ApiModelProperty("修改时间")
    protected LocalDateTime modifyDate;

    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    @Column(updatable = false)
    @ApiModelProperty("创建人")
    protected String createUser;

    @TableField(value = "MODIFY_USER", fill = FieldFill.UPDATE)
    @Column(name = "MODIFY_USER")
    @ApiModelProperty("修改人")
    protected String modifyUser;

    /**
     * Logic to delete is 1, else is 0
     */
    @TableLogic
    @Column(name = "DELFLAG")
    @TableField("DELFLAG")
    @ApiModelProperty("逻辑删除标志")
    protected Integer delFlag;

    @PrePersist
    public void prePersist() {
        this.delFlag = 0;
        this.createUser = EnvironmentUtil.ofUid();
        this.modifyUser = this.createUser;
        log.debug("异步保存事件 doNothing...");
        this.createDate = LocalDateTime.now();
        this.modifyDate = createDate;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyUser = EnvironmentUtil.ofUid();
        this.modifyDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
