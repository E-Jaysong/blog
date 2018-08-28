package com.pubutech.blog.controller;

import com.github.pagehelper.PageInfo;
import com.pubutech.blog.business.entity.RoleEntity;
import com.pubutech.blog.business.service.SysRoleResourcesService;
import com.pubutech.blog.business.service.SysRoleService;
import com.pubutech.blog.core.shiro.ShiroService;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.framework.base.SimplePageSqlCondition;
import com.pubutech.blog.persistence.bean.SysRole;
import com.pubutech.blog.procotol.request.RoleRequestEntity;
import com.pubutech.blog.util.BeanConvertUtil;
import com.pubutech.blog.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/roles")
@Api(value = "RestRoleController", description = "角色操作相关API")
public class RestRoleController {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysRoleResourcesService roleResourcesService;
    @Autowired
    private ShiroService shiroService;

    @RequiresPermissions("roles")
    @PostMapping("/list")
    @ApiOperation(value="获取所有角色",httpMethod="POST",notes="获取所有角色API")
    public ObjectResponse getAll(@RequestBody SimplePageSqlCondition condition) {
        PageInfo<RoleEntity> pageInfo = roleService.pageRoleInfo(condition);
        return ObjectResponse.success(ResultUtil.tablePage(pageInfo));
    }

    @RequiresPermissions("user:allotRole")
    @PostMapping("/userRoleList")
    @ApiImplicitParam(name = "uid", value = "用户Id", required = true, dataType = "Long")
    @ApiOperation(value="获取用户扮演角色列表API",httpMethod="POST",notes="获取用户扮演角色列表API")
    public ObjectResponse userRoleList(Long uid) {
        List<Map<String, Object>> list = roleService.queryRoleListWithSelected(uid);
        ArrayList<RoleRequestEntity> lists = new ArrayList<>();
        RoleRequestEntity temp = null;
        for(Map<String, Object> map :list){
            temp = new RoleRequestEntity();
            temp.setId((Long)map.get("id"));
            temp.setName((String) map.get("name"));
            temp.setSelected((Integer) map.get("checked"));
        }
        return ObjectResponse.success(lists);
    }

    @RequiresPermissions("role:allotResource")
    @PostMapping("/saveRoleResources")
    @ApiOperation(value="增加角色资源",httpMethod="POST",notes="增加角色资源API接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "resourcesId", value = "资源Id", required = true, dataType = "String")
    })
    public ObjectResponse saveRoleResources(Long roleId, String resourcesId) {
        roleResourcesService.addRoleResources(roleId, resourcesId);
        // 重新加载所有拥有roleId的用户的权限信息
        shiroService.reloadAuthorizingByRoleId(roleId);
        return ObjectResponse.success();
    }

    @RequiresPermissions("role:add")
    @PostMapping(value = "/add")
    @ApiOperation(value="新建角色",httpMethod="POST",notes="新建角色API")
    public ObjectResponse add(@RequestBody @Validated RoleRequestEntity entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ObjectResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        roleService.insert(BeanConvertUtil.doConvert(entity, SysRole.class));
        return ObjectResponse.success("成功");
    }

    @RequiresPermissions(value = {"role:batchDelete", "role:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    @ApiOperation(value="删除角色",httpMethod="POST",notes="删除角色API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Id列表", required = true, allowMultiple = true, paramType = "query", dataType = "Long")
    })
    public ObjectResponse remove(Long[] ids) {
        if (null == ids) {
            return ObjectResponse.fail("请至少选择一条记录");
        }
        for (Long id : ids) {
            roleService.removeByPrimaryKey(id);
            roleResourcesService.removeByRoleId(id);
        }
        return ObjectResponse.success("成功删除 [" + ids.length + "] 个角色");
    }

    @RequiresPermissions("role:get")
    @PostMapping("/get/{id}")
    @ApiImplicitParam(name = "id", value = "请求路径参数", required = true, dataType = "Long", paramType = "path")
    @ApiOperation(value="获取角色",httpMethod="POST",notes="获取角色接口API")
    public ObjectResponse get(@PathVariable Long id) {
        return ObjectResponse.success(
                BeanConvertUtil.doConvert(roleService.getByPrimaryKey(id),RoleRequestEntity.class));
    }

    @RequiresPermissions("role:edit")
    @PostMapping("/edit")
    @ApiOperation(value="编辑角色",httpMethod="POST",notes="编辑角色API")
    public ObjectResponse edit(@RequestBody @Validated RoleRequestEntity entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ObjectResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            roleService.updateSelective(BeanConvertUtil.doConvert(entity,SysRole.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.fail("角色修改失败！");
        }
        return ObjectResponse.success();
    }
}
