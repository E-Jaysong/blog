package com.pubutech.blog.controller;

import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity.User;
import com.pubutech.blog.business.entity.UserEntity;
import com.pubutech.blog.business.service.SysUserRoleService;
import com.pubutech.blog.business.service.SysUserService;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.framework.base.SimplePageSqlCondition;
import com.pubutech.blog.persistence.bean.SysUser;
import com.pubutech.blog.util.BeanConvertUtil;
import com.pubutech.blog.util.PasswordUtil;
import com.pubutech.blog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "RestUserController", description = "用户操作相关API")
public class RestUserController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysUserRoleService userRoleService;

    @RequiresPermissions("users")
    @PostMapping("/list")
    @ApiOperation(value="列出全部用户",httpMethod="POST",notes="列出全部用户API")
    public ObjectResponse list(@RequestBody SimplePageSqlCondition condition) {
        PageInfo<UserEntity> pageInfo = userService.pageUserInfo(condition);
        return ObjectResponse.success(ResultUtil.tablePage(pageInfo));
    }

    /**
     * 保存用户角色
     *
     * @param userId
     * @param roleIds
     *         用户角色
     *         此处获取的参数的角色id是以 “,” 分隔的字符串
     * @return
     */
    @RequiresPermissions("user:allotRole")
    @PostMapping("/saveUserRoles")
    @ApiOperation(value="保存用户角色API操作",httpMethod="POST",notes="保存用户角色API操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "roleIds", value = "角色Id用,分开", required = true, dataType = "String")
    })
    public ObjectResponse saveUserRoles(Long userId, String roleIds) {
        userRoleService.addUserRole(userId, roleIds);
        return ObjectResponse.success();
    }

    @RequiresPermissions("user:add")
    @PostMapping(value = "/add")
    @ApiOperation(value="创建用户",httpMethod="POST",notes="编辑用户接口API")
    public ObjectResponse add(@RequestBody UserEntity user) {
        User u = userService.getByUserName(user.getUsername());
        if (u != null) {
            return ObjectResponse.fail("该用户名["+user.getUsername()+"]已存在！请更改用户名");
        }
        SysUser sysUser = BeanConvertUtil.doConvert(user,SysUser.class);

        Date date = new Date();
        sysUser.setCreateTime(date);
        sysUser.setUpdateTime(date);
        try {
            user.setPassword(PasswordUtil.encrypt(user.getPassword(), user.getUsername()));
            userService.insert(sysUser);
            return ObjectResponse.success("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.fail("error");
        }
    }

    @RequiresPermissions(value = {"user:batchDelete", "user:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    @ApiOperation(value="删除用户",httpMethod="POST",notes="删除用户API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Id列表", required = true, allowMultiple = true, paramType = "query", dataType = "Long")
    })
    public ObjectResponse remove(Long[] ids) {
        if (null == ids) {
            return ObjectResponse.fail("请至少选择一条记录");
        }
        for (Long id : ids) {
            userService.removeByPrimaryKey(id);
            userRoleService.removeByUserId(id);
        }
        return ObjectResponse.success("成功删除 [" + ids.length + "] 个用户");
    }

    @RequiresPermissions("user:get")
    @PostMapping("/get/{id}")
    @ApiImplicitParam(name = "id", value = "请求路径参数", required = true, dataType = "Long", paramType = "path")
    @ApiOperation(value="获取用户",httpMethod="POST",notes="获取用户接口API")
    public ObjectResponse get(@PathVariable Long id) {
        return ObjectResponse.success(BeanConvertUtil.doConvert(userService.getByPrimaryKey(id),UserEntity.class));
    }

    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    @ApiOperation(value="编辑用户",httpMethod="POST",notes="编辑用户接口API")
    public ObjectResponse edit(@RequestBody UserEntity user) {
        try {
            userService.updateSelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.fail("用户修改失败！");
        }
        return ObjectResponse.success();
    }
}
