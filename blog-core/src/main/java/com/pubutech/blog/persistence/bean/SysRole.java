package com.pubutech.blog.persistence.bean;

import com.pubutech.blog.framework.base.AbstractDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends AbstractDao {

    private String name;

    private String description;

    private Boolean available;

    @Transient
    private Integer selected;
}
