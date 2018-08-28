package com.pubutech.blog.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GroupResources {

    private long id;

    private String name;

    private String type;

    private String url;

    private String permission;

    private long parent_id;

    private int sort;

    private Boolean external;

    private Boolean available;

    private String icon;

    private long parentId;

    private String parentName;

    private String parentType;

    private String parentUrl;

    private String parentPermission;

    private long parent_parent_id;

    private int parentSort;

    private Boolean parentExternal;

    private Boolean parentAvailable;

    private String parentIcon;

    private long parentParentId;

    private String parentParentName;

    private String parentParentType;

    private String parentParentUrl;

    private String parentParentPermission;

    private long parentParentParentId;

    private int parentParentSort;

    private Boolean parentParentExternal;

    private Boolean parentParentAvailable;

    private String parentParentIcon;

}
