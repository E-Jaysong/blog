package com.pubutech.blog.business.service;

import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity.User;
import com.pubutech.blog.business.entity.UserEntity;
import com.pubutech.blog.business.entity.UserPwd;
import com.pubutech.blog.framework.base.AbstractService;
import com.pubutech.blog.framework.base.SimplePageSqlCondition;
import com.pubutech.blog.persistence.bean.SysUser;
import com.pubutech.blog.persistence.condition.UserSqlCondition;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SysUserService extends AbstractService<User,Long> {

    @Transactional(rollbackFor = Exception.class)
    SysUser insert(SysUser user);

    @Transactional(rollbackFor = Exception.class)
    boolean updateSelective(UserEntity userEntity);

    /**
     * 分页查询
     *
     * @param condition
     * @return
     */
    PageInfo<User> findPageBreakByCondition(UserSqlCondition condition);

    /**
     * 更新用户最后一次登录的状态信息
     *
     * @param user
     * @return
     */
    User updateUserLastLoginInfo(User user);

    /**
     * 根据用户名查找
     *
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 通过角色Id获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> listByRoleId(Long roleId);

    /**
     * 修改密码
     *
     * @param userPwd
     * @return
     */
    boolean updatePwd(UserPwd userPwd) throws Exception;

    PageInfo<UserEntity> pageUserInfo(SimplePageSqlCondition condition);
}
