package com.eastrobot.kbs.template.service;

import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    Page<UserResp> pageForUser(PageRequest request);
}
