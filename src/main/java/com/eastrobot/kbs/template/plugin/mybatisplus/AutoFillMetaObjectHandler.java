package com.eastrobot.kbs.template.plugin.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.eastrobot.kbs.template.util.EnvironmentUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充处理器 需要被自动填充的字段需要包含@TableField(fill = FieldFill.xxx)注解
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-06-17 17:19
 * @see com.eastrobot.kbs.template.model.entity.BaseEntity
 */
@Component
public class AutoFillMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createDate", new Date(), metaObject);
        this.setInsertFieldValByName("createUser", EnvironmentUtil.ofUid(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("modifyDate", new Date(), metaObject);
        this.setUpdateFieldValByName("modifyUser", EnvironmentUtil.ofUid(), metaObject);
    }
}
