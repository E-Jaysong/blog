package com.pubutech.blog.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Resource {

    private long id;

    private String name;

    private String type;

    private String url;

    private String permisssion;

    private long parent_id;

    private int sort;

    private Boolean external;

    private Boolean available;

    private String icon;

    private boolean checked;

}
