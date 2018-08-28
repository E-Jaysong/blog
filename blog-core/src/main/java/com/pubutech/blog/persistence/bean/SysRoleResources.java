package com.pubutech.blog.persistence.bean;


import com.pubutech.blog.framework.base.AbstractDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;


@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleResources extends AbstractDao {

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "resources_id")
    private Long resourcesId;
}
