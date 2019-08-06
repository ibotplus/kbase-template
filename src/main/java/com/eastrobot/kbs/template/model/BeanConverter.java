package com.eastrobot.kbs.template.model;

import com.eastrobot.kbs.template.model.entity.BaseEntity;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.BaseVO;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * easy to convert A to B
 *
 * @author yogurt_lei
 * @date 2019-08-06 15:15
 */
@Mapper(componentModel = "spring")
public interface BeanConverter {

    Map<String, Method> CACHED_METHOD = new ConcurrentHashMap<>(BeanConverter.class.getDeclaredMethods().length);

    @Mappings({})
    User toEntity(UserReq reqVO);

    @Mappings({})
    UserResp toVO(User user);

    @SneakyThrows
    default <V extends BaseVO, T extends BaseEntity> V toVO(T t) {
        Method method = CACHED_METHOD.putIfAbsent(t.getClass().getSimpleName(),
                this.getClass().getDeclaredMethod("toVO", t.getClass()));
        return (V) method.invoke(this, t);
    }

    @SneakyThrows
    default <V extends BaseVO, T extends BaseEntity> T toEntity(V v) {
        Method method = CACHED_METHOD.putIfAbsent(v.getClass().getSimpleName(),
                this.getClass().getDeclaredMethod("toEntity", v.getClass()));
        return (T) method.invoke(this, v);
    }

    default <V extends BaseVO, T extends BaseEntity> List<T> toEntityList(List<V> voList) {
        return voList.stream().map(v -> (T) this.toEntity(v)).collect(Collectors.toList());
    }

    default <V extends BaseVO, T extends BaseEntity> List<V> toVOList(List<T> entityList) {
        return entityList.stream().map(t -> (V) this.toVO(t)).collect(Collectors.toList());
    }

}
