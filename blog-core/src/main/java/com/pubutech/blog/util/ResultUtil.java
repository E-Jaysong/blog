package com.pubutech.blog.util;

import com.github.pagehelper.PageInfo;
import com.pubutech.blog.framework.base.PageResult;

import java.util.ArrayList;

public class ResultUtil {

    public static PageResult tablePage(PageInfo info) {
        if (info == null) {
            return new PageResult(0L, new ArrayList());
        }
        return new PageResult(info.getTotal(), info.getList());
    }
}
