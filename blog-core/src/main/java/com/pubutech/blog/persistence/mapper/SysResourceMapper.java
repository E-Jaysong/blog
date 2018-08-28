package com.pubutech.blog.persistence.mapper;

import com.pubutech.blog.business.entity.GroupResources;
import com.pubutech.blog.framework.plugin.BaseMapper;
import com.pubutech.blog.persistence.bean.SysResources;
import com.pubutech.blog.persistence.condition.ResourceSqlCondition;
import com.pubutech.blog.persistence.provider.SelectSqlProvider;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysResourceMapper extends BaseMapper<SysResources> {

    @Select("select * from sys_resources ORDER BY parent_id ASC")
    @Results(id = "sysResourcesMap",value = {
            @Result(property="id", javaType=Long.class, column="id"),
            @Result(property = "name", column = "name",javaType = String.class),
            @Result(property = "type", column = "type",javaType = String.class),
            @Result(property = "url", column = "url",javaType = String.class),
            @Result(property = "permission", column = "permission",javaType = String.class),
            @Result(property="parentId", javaType=Long.class, column="parent_id"),
            @Result(property="sort", javaType=Integer.class, column="sort"),
            @Result(property = "external", column = "external",javaType = Boolean.class),
            @Result(property = "available", column = "available",javaType = Boolean.class),
            @Result(property = "icon", column = "icon",javaType = String.class),
            @Result(property = "createTime", column = "create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "update_time",javaType = Timestamp.class),
            @Result(property = "checked", column = "checked",javaType = String.class)
    })
    List<SysResources> listAllOrderByparentId();

    @Select("select * from sys_resources where id = #{id} limit 1 ")
    @ResultMap("sysResourcesMap")
    SysResources findResource(@Param("id") long id);

    @Select("select id as parent_id, " +
            " name as parent_name, " +
            " type as parent_type, " +
            " url as parent_url, " +
            " permission as parent_permission, " +
            " parent_id as parent_parent_id, " +
            " sort as parent_sort, " +
            " external as parent_external, " +
            " available as parent_available, " +
            " icon as parent_icon, " +
            " create_time as parent_createTime, " +
            " update_time as parent_updateTime " +
            " from sys_resources where id = #{id} ")
    @Results(id = "sysResourcesParentMap",value = {
            @Result(property="id", javaType=Long.class, column="parent_id"),
            @Result(property = "name", column = "parent_name",javaType = String.class),
            @Result(property = "type", column = "parent_type",javaType = String.class),
            @Result(property = "url", column = "parent_url",javaType = String.class),
            @Result(property = "permission", column = "parent_permission",javaType = String.class),
            @Result(property="parentId", javaType=Long.class, column="parent_parent_id"),
            @Result(property="sort", javaType=Integer.class, column="parent_sort"),
            @Result(property = "external", column = "parent_external",javaType = Boolean.class),
            @Result(property = "available", column = "parent_available",javaType = Boolean.class),
            @Result(property = "icon", column = "parent_icon",javaType = String.class),
            @Result(property = "createTime", column = "parent_create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "parent_update_time",javaType = Timestamp.class)
    })
    SysResources findParent(@Param("id") long id);

    @Select("select id as node_id, " +
            " name as node_name, " +
            " type as node_name, " +
            " url as node_name, " +
            " permission as node_permission, " +
            " parent_id as node_parent_id, " +
            " sort as node_sort, " +
            " external as node_external, " +
            " available as node_available, " +
            " icon as node_icon, " +
            " create_time as node_createTime, " +
            " update_time as node_updateTime " +
            " from sys_resources where parent_id = #{parentId} ")
    @Results(id = "sysResourcesNodeMap",value = {
            @Result(property="id", javaType=Long.class, column="node_id"),
            @Result(property = "name", column = "node_name",javaType = String.class),
            @Result(property = "type", column = "node_type",javaType = String.class),
            @Result(property = "url", column = "node_url",javaType = String.class),
            @Result(property = "permission", column = "node_permission",javaType = String.class),
            @Result(property="parentId", javaType=Long.class, column="node_parent_id"),
            @Result(property="sort", javaType=Integer.class, column="node_sort"),
            @Result(property = "external", column = "node_external",javaType = Boolean.class),
            @Result(property = "available", column = "node_available",javaType = Boolean.class),
            @Result(property = "icon", column = "node_icon",javaType = String.class),
            @Result(property = "createTime", column = "node_create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "node_update_time",javaType = Timestamp.class)
    })
    List<SysResources> listNode(@Param("parentId") long parentId);

    @SelectProvider(type= SelectSqlProvider.class, method="SysResource_findPageBreakByCondition")
    @ResultMap("sysResourcesBeanMap")
    List<SysResources> findPageBreakByCondition(ResourceSqlCondition condition);

    @SelectProvider(type= SelectSqlProvider.class, method="SysResource_listUserResources")
    @Results(id = "sysResourcesBeanMap",value = {
            @Result(property="id", javaType=Long.class, column="id"),
            @Result(property = "name", column = "name",javaType = String.class),
            @Result(property = "type", column = "type",javaType = String.class),
            @Result(property = "url", column = "url",javaType = String.class),
            @Result(property = "permission", column = "permission",javaType = String.class),
            @Result(property="parentId", javaType=Long.class, column="parent_id"),
            @Result(property="sort", javaType=Integer.class, column="sort"),
            @Result(property = "external", column = "external",javaType = Boolean.class),
            @Result(property = "available", column = "available",javaType = Boolean.class),
            @Result(property = "icon", column = "icon",javaType = String.class),
            @Result(property = "createTime", column = "create_time",javaType = Timestamp.class),
            @Result(property = "updateTime", column = "update_time",javaType = Timestamp.class),
            @Result(property = "checked", column = "checked",javaType = String.class),
            @Result(property = "parent", column = "parent_id",one = @One(select =
                    "com.pubutech.blog.persistence.mapper.SysResourceMapper.findParent")),
            @Result(property = "nodes", column = "id",many = @Many(select =
                    "com.pubutech.blog.persistence.mapper.SysResourceMapper.listNode"))
    })
    List<SysResources> listUserResources(Map<String, Object> map);

    @Select("  SELECT " +
            "            re.id, " +
            "            re.`name`, " +
            "            re.parent_id, " +
            "            re.url, " +
            "            re.type, " +
            "            re.icon, " +
            "            ( " +
            "                CASE " +
            "                WHEN EXISTS ( " +
            "                    SELECT " +
            "                        1 " +
            "                    FROM " +
            "                        sys_role_resources rr " +
            "                    WHERE " +
            "                        rr.resources_id = re.id " +
            "                    AND rr.role_id = #{rid} " +
            "                ) " +
            "                    THEN " +
            "                        'true' " +
            "                    ELSE " +
            "                        'false' " +
            "                    END " +
            "            ) AS checked " +
            "        FROM " +
            "            sys_resources re " +
            "        ORDER BY " +
            "            re.sort ASC")
    @ResultMap("sysResourcesBeanMap")
    List<SysResources> queryResourcesListWithSelected(@Param("rid") Long rid);

    @Select("    SELECT " +
            "            re.id, " +
            "            re.`name`, " +
            "            re.parent_id, " +
            "            re.url, " +
            "            re.type, " +
            "            re.icon, " +
            "            ( " +
            "                CASE " +
            "                WHEN EXISTS ( " +
            "                    SELECT " +
            "                        1 " +
            "                    FROM " +
            "                        sys_role_resources rr " +
            "                    WHERE " +
            "                        rr.resources_id = re.id " +
            "                    AND rr.role_id = #{rid} " +
            "                ) " +
            "                    THEN " +
            "                        'true' " +
            "                    ELSE " +
            "                        'false' " +
            "                    END " +
            "            ) AS checked " +
            "        FROM " +
            "            sys_resources re " +
            "        ORDER BY " +
            "            re.sort ASC")
    @ResultMap("sysResourcesBeanMap")
    List<SysResources> listUrlAndPermission();

    @Select("    SELECT " +
            "            r.id, " +
            "            r.`name`, " +
            "            node.id AS node_id, " +
            "            node.`name` AS node_name, " +
            "            node.parent_id " +
            "        FROM " +
            "            sys_resources r " +
            "        LEFT JOIN sys_resources node ON ( " +
            "            node.parent_id = r.id " +
            "            AND node.available = 1 " +
            "            AND node.type = 'menu' " +
            "        ) " +
            "        WHERE " +
            "            r.available = 1 " +
            "        AND r.type = 'menu' " +
            "        AND (r.url IS NULL OR r.url = '') " +
            "        AND (r.permission IS NULL OR r.permission = '') " +
            "        ORDER BY " +
            "            r.sort ASC, " +
            "            node.sort ASC")
    @ResultMap("sysResourcesBeanMap")
    List<SysResources> listAllAvailableMenu();

    @Select("    SELECT " +
            "            re.id, " +
            "            re.`name`, " +
            "            re.parent_id, " +
            "            re.url, " +
            "            re.permission, " +
            "            re.icon, " +
            "            re.sort " +
            "        FROM " +
            "            sys_resources re " +
            "        INNER JOIN sys_role_resources rr ON re.id = rr.resources_id " +
            "        INNER JOIN sys_user_role ur ON rr.role_id = ur.role_id " +
            "        WHERE " +
            "            ur.user_id = #{userId} " +
            "        AND re.available = 1 " +
            "        ORDER BY " +
            "            re.parent_id ASC, " +
            "            re.sort ASC")
    @ResultMap("sysResourcesBeanMap")
    List<SysResources> listByUserId(@Param("userId") Long userId);

    @SelectProvider(type= SelectSqlProvider.class, method="SysResources_listAllGroupResources")
    @Results(id="sysGroupResourcesMap",value = {
            @Result(property="id", javaType=Long.class, column="id"),
            @Result(property = "name", column = "name",javaType = String.class),
            @Result(property = "type", column = "type",javaType = String.class),
            @Result(property = "url", column = "url",javaType = String.class),
            @Result(property = "permission", column = "permission",javaType = String.class),
            @Result(property="parent_id", javaType=Long.class, column="parent_id"),
            @Result(property="sort", javaType=Integer.class, column="sort"),
            @Result(property = "external", column = "external",javaType = Boolean.class),
            @Result(property = "available", column = "available",javaType = Boolean.class),
            @Result(property = "icon", column = "icon",javaType = String.class),

            @Result(property="parentId", javaType=Long.class, column="parentId"),
            @Result(property = "parentName", column = "parent_name",javaType = String.class),
            @Result(property = "parentType", column = "parent_type",javaType = String.class),
            @Result(property = "parentUrl", column = "parent_url",javaType = String.class),
            @Result(property = "parentPermission", column = "parent_permission",javaType = String.class),
            @Result(property="parent_parent_id", javaType=Long.class, column="parent_parent_id"),
            @Result(property="parentSort", javaType=Integer.class, column="parent_sort"),
            @Result(property = "parentExternal", column = "parent_external",javaType = Boolean.class),
            @Result(property = "parentAvailable", column = "parent_available",javaType = Boolean.class),
            @Result(property = "parentIcon", column = "parent_icon",javaType = String.class),

            @Result(property="parentParentId", javaType=Long.class, column="parent_parentId"),
            @Result(property = "parentParentName", column = "parent_parent_name",javaType = String.class),
            @Result(property = "parentParentType", column = "parent_parent_type",javaType = String.class),
            @Result(property = "parentParentUrl", column = "parent_parent_url",javaType = String.class),
            @Result(property = "parentParentPermission", column = "parent_parent_permission",javaType = String.class),
            @Result(property="parentParentParentId", javaType=Long.class, column="parent_parent_parent_id"),
            @Result(property="parentParentSort", javaType=Integer.class, column="parent_parent_sort"),
            @Result(property = "parentParentExternal", column = "parent_parent_external",javaType = Boolean.class),
            @Result(property = "parentParentAvailable", column = "parent_parent_available",javaType = Boolean.class),
            @Result(property = "parentParentIcon", column = "parent_parent_icon",javaType = String.class)
    })
    List<GroupResources> listAllGroupResources();

    @SelectProvider(type= SelectSqlProvider.class, method="sysResources_listAllGroupResourcesByUserName")
    @ResultMap("sysGroupResourcesMap")
    List<GroupResources> listAllGroupResourcesByUserName(@Param("userName") String userName);

    @SelectProvider(type= SelectSqlProvider.class, method="sysResources_listAllGroupResourcesByUserId")
    @ResultMap("sysGroupResourcesMap")
    List<GroupResources> listAllGroupResourcesByUserId(@Param("userId") long userId);

    @Select("select r.* from sys_resources as r  " +
            " LEFT JOIN sys_role_resources as rr ON r.id = rr.resources_id " +
            " LEFT JOIN sys_user_role as ur on rr.role_id = ur.role_id " +
            " where ur.user_id = #{userId} " +
            " ORDER BY r.parent_id ASC ")
    @ResultMap("sysResourcesMap")
    List<SysResources> listAllGroupResourcesByUserIdOrderByParentId(@Param("userId") long userId);

    @Select("SELECT re.*, ( CASE WHEN EXISTS ( SELECT 1 FROM sys_role_resources rr WHERE rr.resources_id = re.id AND rr.role_id = #{rid} ) THEN 'true' ELSE 'false' END ) AS checked FROM sys_resources re ORDER BY re.parent_id ASC ,re.sort ASC;")
    @ResultMap("sysResourcesMap")
    List<SysResources> listResourcesByRoleId(@Param("rid") Long rid);
}
