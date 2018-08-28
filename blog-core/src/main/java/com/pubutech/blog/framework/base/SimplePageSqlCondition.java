package com.pubutech.blog.framework.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SimplePageSqlCondition", description = "分页查询基本请求参数封装类")
public class SimplePageSqlCondition {

    @ApiModelProperty(value = "当前页")
    private int pageNumber = 1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize = 0;

    @ApiModelProperty(value = "分页开始")
    private int pageStart = 0;

    public final static int DEFAULT_PAGE_SIZE = 10;

    public int getPageSize() {
        return pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public int getPageStart() {
        return pageNumber > 0 ? (pageNumber - 1) * getPageSize() : 0;
    }
}
