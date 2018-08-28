package com.pubutech.blog.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourcesGroup extends Resource{

    private ArrayList<ResourcesGroup> list;
}
