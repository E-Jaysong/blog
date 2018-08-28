package com.pubutech.blog.persistence.bean;

import com.pubutech.blog.framework.base.AbstractDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends AbstractDao {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

}
