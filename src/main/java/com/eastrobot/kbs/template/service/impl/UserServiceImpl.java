package com.eastrobot.kbs.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eastrobot.kbs.template.dao.mapper.UserMapper;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.UserVO;
import com.eastrobot.kbs.template.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public Boolean save(UserVO vo) {
        User user = User.builder()
                // .id(vo.getId())
                .username(vo.getUsername())
                .password(vo.getPassword())
                .mainStationId(vo.getMainStationId())
                .visit(vo.getVisit())
                .userInfoId(vo.getUserInfoId())
                .build();
        return super.save(user);
    }

    @Override
    public Boolean update(UserVO vo) {
        User user = User.builder()
                // .id(vo.getId())
                .username(vo.getUsername())
                .password(vo.getPassword())
                .mainStationId(vo.getMainStationId())
                .visit(vo.getVisit())
                .userInfoId(vo.getUserInfoId())
                .build();
        return super.saveOrUpdate(user);
    }
}
