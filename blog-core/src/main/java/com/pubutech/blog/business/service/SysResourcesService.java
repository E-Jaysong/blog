package com.pubutech.blog.business.service;

import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity.Resources;
import com.pubutech.blog.business.entity.ResourcesGroup;
import com.pubutech.blog.framework.base.AbstractService;
import com.pubutech.blog.persistence.bean.SysResources;
import com.pubutech.blog.persistence.condition.ResourceSqlCondition;

import java.util.List;
import java.util.Map;

public interface SysResourcesService  extends AbstractService<Resources, Long> {

    SysResources insert(SysResources sysResources);

    boolean updateSelective(SysResources sysResources);

    /**
     * 分页查询
     *
     * @param condition
     * @return
     */
    PageInfo<Resources> findPageBreakByCondition(ResourceSqlCondition condition);

    /**
     * 获取用户的资源列表
     *
     * @param map
     * @return
     */
    List<Resources> listUserResources(Map<String, Object> map);

    /**
     * 获取ztree使用的资源列表
     *
     * @param rid
     * @return
     */
    List<Map<String, Object>> queryResourcesListWithSelected(Long rid);

    /**
     * 获取资源的url和permission
     *
     * @return
     */
    List<Resources> listUrlAndPermission();

    /**
     * 获取所有可用的菜单资源
     *
     * @return
     */
    List<Resources> listAllAvailableMenu();

    /**
     * 获取用户关联的所有资源
     *
     * @param userId
     * @return
     */
    List<Resources> listByUserId(Long userId);

    List<ResourcesGroup> listAllGroupResources();

    List<ResourcesGroup> listAllGroupResourcesByUserName(String userName);

    List<ResourcesGroup> listAllGroupResourcesByUserId(long userId);

    List<ResourcesGroup> listAllResourcesGroup();

    List<ResourcesGroup> listResourcesByRoleId(long roleId);
}
