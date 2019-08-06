package com.eastrobot.kbs.template.service.impl;

import com.eastrobot.kbs.template.dao.repository.UserRepository;
import com.eastrobot.kbs.template.exception.WrongEntityIdException;
import com.eastrobot.kbs.template.model.BeanConverter;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import com.eastrobot.kbs.template.service.IUserService;
import com.eastrobot.kbs.template.util.pageable.PageInfo;
import com.eastrobot.kbs.template.util.pageable.PageInfoRequest;
import com.eastrobot.kbs.template.util.pageable.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yogurt_lei
 * @since 2019-06-19
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private BeanConverter beanConverter;

    @Override
    public UserResp saveOrUpdate(UserReq userReq) {
        return Optional.of(userReq)
                .map(beanConverter::toEntity)
                .map(userRepository::save)
                .map(beanConverter::toVO)
                .get();
    }

    @Override
    public Boolean deleteById(String id) {
        return userRepository.deleteById(id, true);
    }

    @Override
    public UserResp findById(String id) {
        return userRepository.findById(id).map(t -> beanConverter.toVO(t)).orElseThrow(WrongEntityIdException::new);
    }

    @Override
    public PageInfo<UserResp> pageForResult(PageInfoRequest request) {
        PageRequest pageRequest = PageUtil.ofReq(request);
        Page<User> page = userRepository.findAll(pageRequest);
        return Optional.of(page)
                .filter(p -> !p.isEmpty())
                .map(Slice::getContent)
                .map(ts -> {
                    List<UserResp> content = beanConverter.toVOList(ts);
                    return PageUtil.fillPage(content, pageRequest, page.getTotalElements());
                })
                .orElseGet(PageUtil::emptyPage);
    }
}
