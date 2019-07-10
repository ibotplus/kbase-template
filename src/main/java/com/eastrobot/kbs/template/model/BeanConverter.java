package com.eastrobot.kbs.template.model;

import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.BiztplVO;
import com.eastrobot.kbs.template.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanConverter {
    BeanConverter INSTANCE = Mappers.getMapper(BeanConverter.class);

    @Mappings({
            @Mapping(source = "username", target = "username")
    })
    User fromVO(UserVO vo);

    @Mappings(
            {@Mapping(source = "cateId", target = "cateId")}
    )
    Biztpl fromVO(BiztplVO vo);
}
