package com.pubutech.blog.persistence.mapper;

import com.pubutech.blog.framework.plugin.BaseMapper;
import com.pubutech.blog.persistence.bean.SysUser;
import com.pubutech.blog.persistence.condition.UserSqlCondition;
import com.pubutech.blog.persistence.provider.SelectSqlProvider;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    @SelectProvider(type= SelectSqlProvider.class, method="SysUser_findPageBreakByCondition")
    @ResultMap("sysUserBeanMap")
    List<SysUser> findPageBreakByCondition(UserSqlCondition condition);

    @Select("  SELECT " +
            "   s.id, " +
            "   s.username, " +
            "   s.nickname " +
            "  FROM " +
            "   sys_user s " +
            "  INNER JOIN sys_user_role sur ON  sur.user_id = s.id " +
            "  WHERE " +
            "   sur.role_id = #{roleId}")
    @Results(id = "sysUserBeanMap",value = {
            @Result(property="id", javaType=Long.class, column="id"),
            @Result(property = "username", column = "username",javaType = String.class),
            @Result(property = "password", column = "password",javaType = String.class),
            @Result(property = "nickname", column = "nickname",javaType = Boolean.class),
            @Result(property = "mobile", column = "mobile",javaType = String.class),
            @Result(property = "email", column = "email",javaType = String.class),
            @Result(property = "qq", column = "qq",javaType = String.class),
            @Result(property = "birthday", column = "birthday",javaType = Date.class),
            @Result(property = "gender", column = "gender",javaType = Integer.class),
            @Result(property = "avatar", column = "avatar",javaType = String.class),
            @Result(property = "userType", column = "user_type",javaType = String.class),
            @Result(property = "company", column = "company",javaType = String.class),
            @Result(property = "blog", column = "blog",javaType = String.class),
            @Result(property = "location", column = "location",javaType = String.class),
            @Result(property = "source", column = "source",javaType = String.class),
            @Result(property = "privacy", column = "privacy",javaType = Integer.class),
            @Result(property = "notification", column = "notification",javaType = Integer.class),
            @Result(property = "score", column = "score",javaType = Integer.class),
            @Result(property = "experience", column = "experience",javaType = Integer.class),
            @Result(property = "regIp", column = "reg_ip",javaType = String.class),
            @Result(property = "lastLoginIp", column = "last_login_ip",javaType = String.class),
            @Result(property = "lastLoginTime", column = "last_login_time",javaType = Timestamp.class),
            @Result(property = "loginCount", column = "login_count",javaType = Integer.class),
            @Result(property = "remark", column = "remark",javaType = String.class),
            @Result(property = "createTime", column = "node_create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "node_update_time",javaType = Timestamp.class)
    })
    List<SysUser> listByRoleId(@Param("roleId") Long roleId);

}
