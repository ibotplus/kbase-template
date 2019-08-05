package com.eastrobot.kbs.template.service;

import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import com.eastrobot.kbs.template.util.pageable.PageInfo;
import com.eastrobot.kbs.template.util.pageable.PageInfoRequest;

/**
 * <p>
 * simple demo for entity curd
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19
 */
public interface IUserService {

    String save(UserReq vo);

    Boolean update(UserReq vo);

    Boolean deleteById(String id);

    UserResp findById(String id);

    PageInfo<UserResp> pageForUser(PageInfoRequest request);
}
