package com.pubutech.blog.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEntity {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Integer selected;

}
