package com.eastrobot.kbs.template.service;

import com.eastrobot.kbs.template.model.vo.BaseVO;
import com.eastrobot.kbs.template.util.pageable.PageInfo;
import com.eastrobot.kbs.template.util.pageable.PageInfoRequest;

public interface IBaseService<V extends BaseVO, R extends BaseVO> {

    R saveOrUpdate(V v);

    Boolean deleteById(String id);

    R findById(String id);

    PageInfo<R> pageForResult(PageInfoRequest request);
}
