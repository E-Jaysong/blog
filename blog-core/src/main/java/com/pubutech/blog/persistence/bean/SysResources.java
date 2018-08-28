package com.pubutech.blog.persistence.bean;

import com.pubutech.blog.framework.base.AbstractDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysResources extends AbstractDao {

    private String name;

    private String type;

    private String url;

    private String permission;

    @Column(name = "parent_id")
    private Long parentId;

    private Integer sort;

    private Boolean external;

    private Boolean available;

    private String icon;

    @Transient
    private String checked;
    @Transient
    private SysResources parent;
    @Transient
    private List<SysResources> nodes;
}
