package com.eastrobot.kbs.template.util;

import org.springframework.data.domain.*;

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
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 获取分页参数page
     *
     * @param pageIndex 指定页
     * @param pageSize  页大小
     * @param sort      排序属性
     */
    public static PageRequest of(int pageIndex, int pageSize, Sort sort) {
        pageIndex = pageIndex < 1 ? DEFAULT_PAGE_INDEX : pageIndex - 1;
        pageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;

        if (sort == null) {
            return PageRequest.of(pageIndex, pageSize);
        }
        return PageRequest.of(pageIndex, pageSize, sort);
    }

    /**
     * 获取分页参数page
     *
     * @param pageIndexStr 指定页
     * @param pageSizeStr  页大小
     * @param sort         排序属性
     */
    public static PageRequest of(String pageIndexStr, String pageSizeStr, Sort sort) {
        return of(Integer.parseInt(pageIndexStr), Integer.parseInt(pageSizeStr), sort);
    }

    /**
     * 获取单结果分页参数
     */
    public static PageRequest ofSingle() {
        return of(0, 1, null);
    }

    /**
     * 获取分页参数 默认第0页,页大小20
     */
    public static PageRequest of() {
        return of(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE, null);
    }

    /**
     * 获取分页参数 指定页,指定页大小
     *
     * @param pageIndex 指定页
     * @param pageSize  页大小
     */
    public static PageRequest of(int pageIndex, int pageSize) {
        return of(pageIndex, pageSize, null);
    }

    /**
     * 获取分页参数 指定页,指定页大小
     *
     * @param pageIndexStr 指定页
     * @param pageSizeStr  页大小
     */
    public static PageRequest of(String pageIndexStr, String pageSizeStr) {
        return of(Integer.parseInt(pageIndexStr), Integer.parseInt(pageSizeStr), null);
    }

    /**
     * 填充页面
     *
     * @param content 页内容
     * @param total   页总数量
     * @param <T>     填充内容
     */
    public static <T> Page<T> fillPage(List<T> content, long total) {
        return new PageImpl<>(content, Pageable.unpaged(), total);
    }

    public static <T> Page<T> emptyPage() {
        return fillPage(Collections.emptyList(), 0);
    }
}
