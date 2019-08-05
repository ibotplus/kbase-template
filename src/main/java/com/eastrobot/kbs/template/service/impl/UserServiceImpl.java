package com.eastrobot.kbs.template.service.impl;

import com.eastrobot.kbs.template.dao.repository.UserRepository;
import com.eastrobot.kbs.template.exception.BusinessException;
import com.eastrobot.kbs.template.exception.WrongEntityIdException;
import com.eastrobot.kbs.template.model.BeanConverter;
import com.eastrobot.kbs.template.model.entity.BaseEntity;
import com.eastrobot.kbs.template.model.entity.User;
import com.eastrobot.kbs.template.model.vo.req.UserReq;
import com.eastrobot.kbs.template.model.vo.resp.UserResp;
import com.eastrobot.kbs.template.service.IUserService;
import com.eastrobot.kbs.template.util.pageable.PageInfo;
import com.eastrobot.kbs.template.util.pageable.PageInfoRequest;
import com.eastrobot.kbs.template.util.pageable.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private UserRepository repo;

    @Resource
    private BeanConverter beanConverter;

    @Override
    public String save(UserReq vo) {
        return Optional.ofNullable(beanConverter.convert(vo))
                .map(v -> repo.save(v))
                .map(BaseEntity::getId)
                .orElseThrow(() -> new BusinessException("can't create user"));
    }

    @Override
    public Boolean update(UserReq vo) {
        return Optional.ofNullable(beanConverter.convert(vo))
                .map(v -> repo.save(v))
                .map(BaseEntity::getId)
                .filter(StringUtils::isNotEmpty)
                .isPresent();
    }

    @Override
    public Boolean deleteById(String id) {
        // logic delete
        repo.deleteById(id, true);
        // not recommend， physical delete
        // repo.deleteById(id,false);
        return true;
    }

    @Override
    public UserResp findById(String id) {
        return Optional.ofNullable(repo.findById(id).orElseThrow(WrongEntityIdException::new))
                .map(beanConverter::convert)
                .get();
    }

    @Override
    public PageInfo<UserResp> pageForUser(PageInfoRequest request) {
        PageRequest pageRequest = PageUtil.ofReq(request);
        Page<User> page = repo.findAll(pageRequest);
        return Optional.of(page)
                .filter(p -> !p.isEmpty())
                .map(Slice::getContent)
                .map(users -> PageUtil.fillPage(beanConverter.convert(users), pageRequest, page.getTotalElements()))
                .orElseGet(PageUtil::emptyPage);
    }
}
