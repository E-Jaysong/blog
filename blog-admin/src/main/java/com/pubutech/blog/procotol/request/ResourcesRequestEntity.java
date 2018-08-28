package com.pubutech.blog.procotol.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ResourcesRequestEntity", description = "资源操作基本请求类")
public class ResourcesRequestEntity {


    //@NotNull(message = "资源ID不可为空")
    @ApiModelProperty(value = "资源ID")
    private long id;

    @NotNull(message = "资源Name不可为空")
    @ApiModelProperty(value = "资源Name")
    private String name;

    @NotNull(message = "资源类型不可为空")
    @ApiModelProperty(value = "资源Type(menu/button)")
    private String type;

    @ApiModelProperty(value = "资源访问链接")
    private String url;

    @ApiModelProperty(value = "资源访问需要权限")
    private String permisssion;

    @NotNull(message = "父资源Id不可为空")
    @ApiModelProperty(value = "父资源Id")
    private long parentId;

    @NotNull(message = "资源排序不可为空")
    @ApiModelProperty(value = "资源排序")
    private int sort;

    @NotNull(message = "是否外部资源不可为空")
    @ApiModelProperty(value = "是否外部资源资源")
    private Boolean external;

    @NotNull(message = "资源是否可用不可为空")
    @ApiModelProperty(value = "资源是否可用")
    private Boolean available;

    @ApiModelProperty(value = "资源使用icon")
    private String icon;

    private boolean checked;

}
