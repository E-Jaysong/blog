package com.pubutech.blog.persistence.provider;

import com.pubutech.blog.framework.base.BaseSqlConditionBean;
import com.pubutech.blog.persistence.condition.ResourceSqlCondition;
import com.pubutech.blog.persistence.condition.RoleSqlCondition;
import com.pubutech.blog.persistence.condition.UserSqlCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;

public class SelectSqlProvider {

    public String findNormalSqlExample(final BaseSqlConditionBean condition) {
        String sql = "SELECT * FROM sys_role as com";
        if(StringUtils.isEmpty(condition.getKeywords())){
            sql += " where com.description like  #{keywords} % ";
        }
        return sql;
    }

    public String SysRole_findPageBreakByCondition(final RoleSqlCondition condition){
        return new SQL(){{
            SELECT("*");
            FROM("sys_role as com");
            StringBuilder whereClause = new StringBuilder();
            if(!StringUtils.isEmpty(condition.getKeywords())){
                whereClause.append(" and com.description like '%").append("#{keywords}").append("%' ");
            }
            if(!"".equals(whereClause.toString())){
                WHERE(whereClause.toString().replaceFirst("and", ""));
            }
            ORDER_BY("com.create_time DESC");
        }}.toString();
    }

    public String SysResource_findPageBreakByCondition(final ResourceSqlCondition condition){
        String sql  = "    SELECT " +
                "            com.*, " +
                "            f.id parent_id, " +
                "            f.`name` parent_name, " +
                "            f.`icon` parent_icon, " +
                "            f.type parent_type " +
                "        FROM " +
                "            sys_resources com " +
                "        LEFT JOIN sys_resources f ON com.parent_id = f.id " ;
        if(!StringUtils.isEmpty(condition.getKeywords())){
            sql += " WHERE " +
                 " com.name like  #{keywords}  OR " + " com.url like  #{keywords}  OR " + " com.permission like  #{keywords} ";
        }

        sql +=
                "        ORDER by " +
                "            com.parent_id ASC, " +
                "            com.name DESC";
        return sql;
    }
    public String SysResource_listUserResources(final HashMap<String, Object> map){
        String sql = "SELECT " +
                "            re.id, " +
                "            re.`name`, " +
                "            re.parent_id, " +
                "            re.url, " +
                "            re.permission, " +
                "            re.icon, " +
                "            node.id AS node_id, " +
                "            node.`name` AS node_name, " +
                "            node.`type` AS node_type, " +
                "            node.`url` AS node_url, " +
                "            node.parent_id AS node_parent_id, " +
                "            node.`permission` AS node_permission, " +
                "            node.`available` AS node_available, " +
                "            node.icon AS node_icon " +
                "        FROM " +
                "            sys_resources re " +
                "        LEFT JOIN sys_role_resources rr ON re.id = rr.resources_id " +
                "        LEFT JOIN sys_user_role ur ON rr.role_id = ur.role_id " +
                "        LEFT JOIN sys_resources node ON node.parent_id = re.id AND node.available = 1 " +
                "        WHERE " +
                "            (re.parent_id = 0 OR re.parent_id IS NULL ) " +
                "        AND re.available = 1";
        if(map.get("userId")!=null){
            sql += " AND ur.user_id = #{userId} ";
        }
        if(map.get("type")!=null){
            sql += " AND re.type= #{type} ";
        }
        sql += "        ORDER BY " +
                "            re.sort ASC, " +
                "            node.sort ASC";
        return sql;
    }

    public String SysUser_findPageBreakByCondition(final UserSqlCondition condition){
        String sql = "  SELECT " +
                "   s.* " +
                "  FROM " +
                "   sys_user s " +
                "  WHERE " +
                "   1 = 1 " ;
        if(!StringUtils.isEmpty(condition.getKeywords())){
            sql += "   AND " +
                    "   ( " +
                    " s.nickname like  #{keywords}  OR " + " s.mobile like  #{keywords}  OR " + " s.username like  #{keywords} OR "+
                    " s.password like  #{keywords}  OR " + " s.email like  #{keywords}  OR " + " s.qq like  #{keywords} OR " +
                    "  s.remark like  #{keywords} "+"   ) " ;

        }
        if(condition.getUser()!=null ){
            if(condition.getUser().getId() !=null ){
                sql += "    AND s.id = #{user.id} " ;
            }
            if(condition.getUser().getGender() != null){
                sql += "    AND s.gender = #{user.gender} ";
            }
            if(condition.getUser().getUserType()!=null){
                sql += "    AND s.user_type = #{user.userType} " ;
            }
            if(condition.getUser().getUsername() !=null){
                sql += "    AND s.username = #{user.username} " ;
            }
            if(condition.getUser().getPassword() !=null){
                sql += "    AND s.password = #{user.password} ";
            }
            if(condition.getUser().getLastLoginIp() !=null){
                sql += "    AND s.last_login_ip = #{user.lastLoginIp} ";
            }
            if(condition.getUser().getLastLoginTime() != null){
                sql += "    AND s.last_login_time = #{user.lastLoginTime} ";
            }
        }
        sql +=  "  GROUP BY " +
                "   s.id " +
                "  ORDER BY " +
                "   s.create_time DESC";

        return sql;
    }

    public String SysResources_listAllGroupResources(){
        String sql = "select  child.id,child.`name`,child.type,child.url,child.permission,child.parent_id,child.sort,child.icon,child.external,child.available, " +
                " parent.id as parentId ,parent.`name` as parent_name,parent.type as parent_type,parent.url as parent_url,parent.permission as parent_permission,parent.parent_id as parent_parent_id,parent.sort as parent_sort,parent.icon as parent_icon ,parent.external as parent_external,parent.available as parent_available, " +
                " parent_parent.id as parent_parentId ,parent_parent.`name` as parent_parent_name,parent_parent.type as parent_parent_type,parent_parent.url as parent_parent_url,parent_parent.permission as parent_parent_permission,parent_parent.parent_id as parent_parent_parent_id,parent_parent.sort as parent_parent_sort,parent_parent.icon as parent_parent_icon,parent_parent.external as parent_parent_external,parent_parent.available as parent_parent_available " +
                " from sys_resources as child  " +
                " LEFT JOIN sys_resources as parent on child.parent_id != 0 and child.parent_id = parent.id  " +
                " LEFT JOIN sys_resources as parent_parent on parent.parent_id !=0 and parent.parent_id = parent_parent.id " +
                " where !ISNULL(parent_parent.id) and !ISNULL(parent.id) " +
                " Order BY parent_parent.id ASC , parent.id ASC ";
        return sql;
    }

    public String sysResources_listAllGroupResourcesByUserName(final String userName){
        return "select  child.id,child.`name`,child.type,child.url,child.permission,child.parent_id,child.sort,child.icon,child.external,child.available, " +
                " parent.id as parentId ,parent.`name` as parent_name,parent.type as parent_type,parent.url as parent_url,parent.permission as parent_permission,parent.parent_id as parent_parent_id,parent.sort as parent_sort,parent.icon as parent_icon ,parent.external as parent_external,parent.available as parent_available, " +
                " parent_parent.id as parent_parentId ,parent_parent.`name` as parent_parent_name,parent_parent.type as parent_parent_type,parent_parent.url as parent_parent_url,parent_parent.permission as parent_parent_permission,parent_parent.parent_id as parent_parent_parent_id,parent_parent.sort as parent_parent_sort,parent_parent.icon as parent_parent_icon,parent_parent.external as parent_parent_external,parent_parent.available as parent_parent_available " +
                " from sys_resources as child  " +
                " LEFT JOIN sys_resources as parent on child.parent_id != 0 and child.parent_id = parent.id  " +
                " LEFT JOIN sys_resources as parent_parent on parent.parent_id !=0 and parent.parent_id = parent_parent.id " +
                " LEFT JOIN sys_role_resources as role_resource on child.id = role_resource.resources_id " +
                " LEFT JOIN sys_user_role as user_role on user_role.role_id = role_resource.role_id " +
                " LEFT JOIN sys_user as `user` on `user`.id = user_role.user_id " +
                " where `user`.username = #{userName} and !ISNULL(parent_parent.id) and !ISNULL(parent.id) " +
                " Order BY parent_parent.id ASC , parent.id ASC ;";
    }

    public String sysResources_listAllGroupResourcesByUserId(final long userId){
        return "select  child.id,child.`name`,child.type,child.url,child.permission,child.parent_id,child.sort,child.icon,child.external,child.available, " +
                " parent.id as parentId ,parent.`name` as parent_name,parent.type as parent_type,parent.url as parent_url,parent.permission as parent_permission,parent.parent_id as parent_parent_id,parent.sort as parent_sort,parent.icon as parent_icon ,parent.external as parent_external,parent.available as parent_available, " +
                " parent_parent.id as parent_parentId ,parent_parent.`name` as parent_parent_name,parent_parent.type as parent_parent_type,parent_parent.url as parent_parent_url,parent_parent.permission as parent_parent_permission,parent_parent.parent_id as parent_parent_parent_id,parent_parent.sort as parent_parent_sort,parent_parent.icon as parent_parent_icon,parent_parent.external as parent_parent_external,parent_parent.available as parent_parent_available " +
                " from sys_resources as child  " +
                " LEFT JOIN sys_resources as parent on child.parent_id != 0 and child.parent_id = parent.id  " +
                " LEFT JOIN sys_resources as parent_parent on parent.parent_id !=0 and parent.parent_id = parent_parent.id " +
                " LEFT JOIN sys_role_resources as role_resource on child.id = role_resource.resources_id " +
                " LEFT JOIN sys_user_role as user_role on user_role.role_id = role_resource.role_id " +
                " where user_role.user_id = #{userId}  and !ISNULL(parent_parent.id) and !ISNULL(parent.id) " +
                " Order BY parent_parent.id ASC , parent.id ASC ;";
    }
}
