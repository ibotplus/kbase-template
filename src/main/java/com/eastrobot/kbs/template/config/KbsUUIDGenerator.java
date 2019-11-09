/*
 * Powered by http://www.xiaoi.com
 */
package com.eastrobot.kbs.template.config;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @version v1.0
 * @date 2019/8/27 11:41
 */
public class KbsUUIDGenerator extends UUIDHexGenerator {

    private String entityName;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        entityName = params.getProperty(ENTITY_NAME);
        if (entityName == null) {
            throw new MappingException("no entity name");
        }
        super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(entityName, object)
                .getIdentifier(object, session);
        if (id!=null && !"".equals(id)) {
            return id;
        }
        return super.generate(session, object);
    }

}
