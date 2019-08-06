package com.eastrobot.kbs.template.util.pageable;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

/**
 * JPA Util
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2018-08-10 9:54
 */
public class PageUtil {
    /**
     * 默认页 0 为第一页
     */
    private static final int DEFAULT_PAGE_INDEX = 0;
    /**
     * 默认页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static PageRequest ofReq(PageInfoRequest pageInfoRequest) {
        return ofReq(pageInfoRequest.getPageNum(), pageInfoRequest.getPageSize(), null);
    }

    /**
     * 获取分页参数page
     *
     * @param pageIndex 指定页
     * @param pageSize  页大小
     * @param sort      排序属性
     */
    public static PageRequest ofReq(int pageIndex, int pageSize, Sort sort) {
        pageIndex = pageIndex < 1 ? DEFAULT_PAGE_INDEX : pageIndex - 1;
        pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;

        if (sort == null) {
            return PageRequest.of(pageIndex, pageSize);
        }
        return PageRequest.of(pageIndex, pageSize, sort);
    }

    /**
     * 填充页面
     *
     * @param content     页内容
     * @param total       页总数量
     * @param pageRequest 分页请求参数
     * @param <T>         填充内容
     */
    public static <T> PageInfo<T> fillPage(List<T> content, PageRequest pageRequest, long total) {
        return PageInfo.of(new PageImpl<>(content, pageRequest, total));
    }

    public static <T> PageInfo<T> emptyPage() {
        return PageInfo.of(new PageImpl<>(Collections.emptyList()));
    }
}
