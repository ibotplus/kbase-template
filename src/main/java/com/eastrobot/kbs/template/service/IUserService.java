package com.eastrobot.kbs.template.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19
 */
public interface IUserService extends IService<User> {

    Boolean save(UserVO vo);

    Boolean update(UserVO vo);
}
//