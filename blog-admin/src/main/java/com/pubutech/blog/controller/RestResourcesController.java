package com.pubutech.blog.controller;

import com.pubutech.blog.business.entity.ResourcesGroup;
import com.pubutech.blog.business.service.SysResourcesService;
import com.pubutech.blog.core.shiro.ShiroService;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.persistence.bean.SysResources;
import com.pubutech.blog.procotol.request.ResourcesRequestEntity;
import com.pubutech.blog.util.BeanConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resources")
@Api(value = "RestResourcesController", description = "资源操作相关API")
public class RestResourcesController {

    @Resource
    private SysResourcesService resourcesService;

    @Autowired
    private ShiroService shiroService;

    @RequiresPermissions("resources")
    @PostMapping("/list")
    @ApiOperation(value="获取登陆账户所有所有能操作的资源列表",httpMethod="POST",notes="获取登陆账户所有所有能操作的资源列表API")
    public ObjectResponse list() {

        Long userId = (Long) SecurityUtils.getSubject().getPrincipal();

        List<ResourcesGroup> list = resourcesService.listAllGroupResourcesByUserId(userId);
        //PageInfo<Resources> pageInfo = resourcesService.findPageBreakByCondition(condition);
        return ObjectResponse.success(list);
    }

    @RequiresPermissions("role:allotResource")
    @PostMapping("/resourcesWithSelected")
    @ApiOperation(value="获取角色目前授权和未授权的资源",httpMethod="POST",notes="获取角色目前授权和未授权的资源API")
    @ApiImplicitParam(name = "rid", value = "角色Id", required = true, dataType = "Long" )
    public ObjectResponse resourcesWithSelected(long rid) {
        List<ResourcesGroup> list = resourcesService.listResourcesByRoleId(rid);

        return ObjectResponse.success(list);
    }

    @RequiresPermissions("resource:add")
    @PostMapping(value = "/add")
    @ApiOperation(value="新建资源",httpMethod="POST",notes="新建资源API")
    public ObjectResponse add(@RequestBody @Validated ResourcesRequestEntity entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ObjectResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        SysResources sysResources = BeanConvertUtil.doConvert(entity,SysResources.class);
        resourcesService.insert(sysResources);
        //更新权限
        shiroService.updatePermission();
        return ObjectResponse.success();
    }

    @RequiresPermissions(value = {"resource:batchDelete", "resource:delete"}, logical = Logical.OR)
    @PostMapping(value = "/remove")
    @ApiOperation(value="删除资源",httpMethod="POST",notes="删除资源API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Id列表", required = true, allowMultiple = true, paramType = "query", dataType = "Long")
    })
    public ObjectResponse remove(Long[] ids) {
        if (null == ids) {
            return ObjectResponse.fail("请至少选择一条记录");
        }
        for (Long id : ids) {
            resourcesService.removeByPrimaryKey(id);
        }

        //更新权限
        shiroService.updatePermission();
        return ObjectResponse.success("成功删除 [" + ids.length + "] 个资源");
    }

    @RequiresPermissions("resource:get")
    @PostMapping("/get/{id}")
    @ApiImplicitParam(name = "id", value = "请求路径参数", required = true, dataType = "Long", paramType = "path")
    @ApiOperation(value="获取资源",httpMethod="POST",notes="获取资源接口API")
    public ObjectResponse get(@PathVariable Long id) {
        return ObjectResponse.success(
                BeanConvertUtil.doConvert(resourcesService.getByPrimaryKey(id),ResourcesRequestEntity.class));
    }

    @RequiresPermissions("resource:edit")
    @PostMapping("/edit")
    @ApiOperation(value="编辑资源",httpMethod="POST",notes="编辑资源接口API")
    public ObjectResponse edit(@RequestBody @Validated ResourcesRequestEntity entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ObjectResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        try {
            resourcesService.updateSelective(BeanConvertUtil.doConvert(entity,SysResources.class));
        } catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.fail("资源修改失败！");
        }
        return ObjectResponse.success();
    }

}
