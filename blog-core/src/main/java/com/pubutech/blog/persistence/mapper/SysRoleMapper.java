package com.pubutech.blog.persistence.mapper;

import com.pubutech.blog.framework.plugin.BaseMapper;
import com.pubutech.blog.persistence.bean.SysRole;
import com.pubutech.blog.persistence.condition.RoleSqlCondition;
import com.pubutech.blog.persistence.provider.SelectSqlProvider;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select * from sys_role")
    @Results(id = "sysRoleBeanMap",value = {
            @Result(property="id", javaType=Long.class, column="id"),
            @Result(property = "name", column = "name",javaType = String.class),
            @Result(property = "description", column = "description",javaType = String.class),
            @Result(property = "available", column = "available",javaType = Boolean.class),
            @Result(property = "createTime", column = "create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "update_time",javaType = Timestamp.class),
            @Result(property = "selected", column = "selected",javaType = Timestamp.class)
    })
    List<SysRole> listAll();

    @SelectProvider(type= SelectSqlProvider.class, method="SysRole_findPageBreakByCondition")
    @ResultMap("sysRoleBeanMap")
    List<SysRole> findPageBreakByCondition(RoleSqlCondition condition);

    @Select("SELECT r.id, r.name, r.description, " +
            "( CASE WHEN ( SELECT ur.role_id FROM sys_user_role ur WHERE ur.user_id = #{userId} AND ur.role_id = r.id ) THEN 1 ELSE 0 END ) " +
            " AS selected FROM sys_role r")
    @ResultMap("sysRoleBeanMap")
    List<SysRole> queryRoleListWithSelected(@Param("userId") long userId);

    @Select(" SELECT " +
            "   r.id, " +
            "   r.name, " +
            "   r.description " +
            " FROM " +
            " sys_role r " +
            "   INNER JOIN sys_user_role ur ON ur.role_id = r.id " +
            " WHERE " +
            " ur.user_id = #{userId}")
    @ResultMap("sysRoleBeanMap")
    List<SysRole> listRolesByUserId(@Param("userId") long userId);

}
