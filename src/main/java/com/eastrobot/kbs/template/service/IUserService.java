package com.eastrobot.kbs.template.service;

import com.eastrobot.kbs.template.model.vo.UserVO;
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

    String save(UserVO vo);

    Boolean update(UserVO vo);

    Boolean deleteById(String id);

    UserVO findById(String id);

    Page<UserVO> pageForUser(PageRequest request);
}
