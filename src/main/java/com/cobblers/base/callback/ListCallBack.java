package com.cobblers.base.callback;

import java.util.List;

/**
 * list回调
 **/
public interface ListCallBack<T> {
    /**
     * 回调数据列表
     * @param currentPage 当前页
     * @param pageSize 条数
     * @param query 关键字
     * @return list
     */
    List<T> listCallBack(int currentPage, int pageSize, String query);
}
