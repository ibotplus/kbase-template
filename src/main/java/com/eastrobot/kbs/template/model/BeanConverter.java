package com.eastrobot.kbs.template.model;

import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.req.BiztplReq;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanConverter {
    BeanConverter INSTANCE = Mappers.getMapper(BeanConverter.class);

    @Mappings({})
    User convert(UserReq vo);

    @Mappings({})
    UserResp convert(User user);

    @Mappings(
            {@Mapping(source = "cateId", target = "cateId")}
    )
    Biztpl convert(BiztplReq vo);

}
