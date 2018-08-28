package com.pubutech.blog.persistence.mapper;

import com.pubutech.blog.framework.plugin.BaseMapper;
import com.pubutech.blog.persistence.bean.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("        SELECT " +
            "            user_id " +
            "        FROM " +
            "            sys_user_role " +
            "        WHERE " +
            "            role_id = #{roleId}")
    List<Integer> findUserIdByRoleId(Integer roleId);

}
