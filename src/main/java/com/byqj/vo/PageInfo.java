package com.byqj.vo;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    // 总条数
    private Long total;
    // 当前页码
    private int pageNum;
    // 一页条数
    private int pageSize;
    // 总页数
    private int pageCount;
    // 前一页页码
    private int pagePrev;
    // 后一页页码
    private int pageNext;
    // 数据信息
    private List<T> list;

    public PageInfo() {
        this.total = 0L;
        this.pageCount = 0;
        this.pageNum = 0;
        this.pageSize = 0;
        this.pagePrev = 0;
        this.pageNext = 0;
    }

    public PageInfo(List<T> list, long total, Integer pageNum, Integer pageSize) {

        if (pageNum == null || pageSize == null)
            pageNum = pageSize = 1;

        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageCount = (int) Math.ceil(1.0 * total / pageSize);
        this.pagePrev = pageNum > 1 ? pageNum - 1 : 1;
        this.pageNext = pageNum < this.pageCount ? pageNum + 1 : this.pageCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPagePrev() {
        return pagePrev;
    }

    public void setPagePrev(int pagePrev) {
        this.pagePrev = pagePrev;
    }

    public int getPageNext() {
        return pageNext;
    }

    public void setPageNext(int pageNext) {
        this.pageNext = pageNext;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
