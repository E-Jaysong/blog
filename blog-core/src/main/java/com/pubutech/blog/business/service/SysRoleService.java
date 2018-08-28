package com.pubutech.blog.business.service;


import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity.Role;
import com.pubutech.blog.business.entity.RoleEntity;
import com.pubutech.blog.framework.base.AbstractService;
import com.pubutech.blog.framework.base.SimplePageSqlCondition;
import com.pubutech.blog.persistence.bean.SysRole;
import com.pubutech.blog.persistence.condition.RoleSqlCondition;

import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 */
public interface SysRoleService extends AbstractService<Role, Long> {


    SysRole insert(SysRole entity);

    /**
     * 获取ztree使用的角色列表
     *
     * @param uid
     * @return
     */
    List<Map<String, Object>> queryRoleListWithSelected(Long uid);

    /**
     * 分页查询
     *
     * @param condition
     * @return
     */
    PageInfo<Role> findPageBreakByCondition(RoleSqlCondition condition);

    PageInfo<RoleEntity> pageRoleInfo(SimplePageSqlCondition condition);

    /**
     * 获取用户的角色
     *
     * @param userId
     * @return
     */
    List<Role> listRolesByUserId(Long userId);

    boolean updateSelective(SysRole entity);
}
