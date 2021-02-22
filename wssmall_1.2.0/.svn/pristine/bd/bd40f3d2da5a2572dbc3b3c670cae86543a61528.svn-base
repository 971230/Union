package com.rop.core.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明: 分页信息的封装类，记录了当前页面信息和当前页的数据。
 */
public class PageModel implements Serializable {

    //总记录数
    private int totalCount;

    //当前页索引
    private int pageIndex;

    //总页数
    private int pageCount;

    //每页的记录行数
    private int pageSize;

    //当前页数据的对象列表
    private List list;

    /**
     * 初始化是空的list。
     * <p/>
     * PageModel.java构建器
     */
    public PageModel() {

        this.totalCount = 0;
        this.pageIndex = 1;
        this.pageCount = 1;
        this.pageSize = 10;
        this.list = new ArrayList();

    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


}

