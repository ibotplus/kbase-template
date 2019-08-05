package com.eastrobot.kbs.template.model;

import com.eastrobot.kbs.template.model.entity.BaseEntity;
import com.eastrobot.kbs.template.model.entity.Biztpl;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.BaseVO;
import com.eastrobot.kbs.template.model.vo.req.BiztplReq;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeanConverter {

    @Mappings({})
    User convert(UserReq reqVO);

    @Mappings({})
    UserResp convert(User user);

    @Mappings({})
    List<UserResp> convert(List<User> user);

    @Mappings(
            {@Mapping(source = "cateId", target = "cateId")}
    )
    Biztpl convert(BiztplReq vo);

    @Mappings({})
    BaseEntity convert(BaseVO vo);

}
