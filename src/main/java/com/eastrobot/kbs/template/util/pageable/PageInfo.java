package com.eastrobot.kbs.template.util.pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * JPA Page Wrapper
 * <p/>
 */
@Data
@ApiModel
public class PageInfo<T> implements Serializable {

    @ApiModelProperty(value = "是否第一页", position = 1)
    private boolean firstPage;

    @ApiModelProperty(value = "是否最后页", position = 2)
    private boolean lastPage;

    @ApiModelProperty(value = "是否有前一页", position = 3)
    private boolean hasPreviousPage;

    @ApiModelProperty(value = "是否有下一页", position = 4)
    private boolean hasNextPage;

    @ApiModelProperty(value = "总页数", position = 5)
    private int pages;

    @ApiModelProperty(value = "总记录数", position = 6)
    private long total;

    @ApiModelProperty(value = "当前页码", position = 7)
    private int pageNum;

    @ApiModelProperty(value = "当前页大小", position = 8)
    private int pageSize;

    @ApiModelProperty(value = "分页结果集", position = 10)
    private List<T> list;

    public PageInfo(){}

    public PageInfo(Page<T> page) {
        this.pageNum = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.pages = page.getTotalPages();
        this.list = page.getContent();
        this.total = page.getTotalElements();
        // 判断页边界
        firstPage = pageNum == 1;
        lastPage = pageNum == pages || pages == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

    public static <T> PageInfo<T> of(Page<T> page) {
        return new PageInfo<>(page);
    }
}
