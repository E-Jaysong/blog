package com.pubutech.blog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pubutech.blog.business.annotation.SysLog;
import com.pubutech.blog.business.entity.Resources;
import com.pubutech.blog.business.entity.ResourcesGroup;
import com.pubutech.blog.business.entity.User;
import com.pubutech.blog.business.service.Impl.SysResourceServiceImpl;
import com.pubutech.blog.business.service.Impl.SysRoleResourcesServiceImpl;
import com.pubutech.blog.business.service.Impl.SysUserServiceImpl;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.persistence.bean.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "RestTestController", description = "测试操作相关API")
public class RestTestController {

    @Autowired
    public SysResourceServiceImpl sysResourceServiceImpl;

    @Autowired
    private SysRoleResourcesServiceImpl sysRoleResourcesServiceImpl;

    @Autowired
    public SysUserServiceImpl sysUserServiceImpl;

    @GetMapping("/ApiImplicitParam/{path}")
    @ApiImplicitParam(name = "path", value = "请求路径参数", required = true, dataType = "Long", paramType = "path")
    @ApiOperation(value="一个请求参数测试API操作",httpMethod="GET",notes="一个请求参数测试API操作接口")
    public ObjectResponse api(@PathVariable(value = "path") String path){

        log.info(path);
        return ObjectResponse.success();
    }

    @GetMapping("/ApiImplicitParams")
    @ApiOperation(value="多个请求参数测试API操作",httpMethod="GET",notes="多个请求参数测试API操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "args1", value = "请求参数1", required = true, dataType = "String"),
            @ApiImplicitParam(name = "args2", value = "请求参数2", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "args3", value = "请求参数13", required = true, dataType = "String")
    })
    @SysLog(remark = "请求返回参数测试")
    public ObjectResponse api2(String args1,Long args2, String args3){
        log.info(args1 + args2 + args3);
        return ObjectResponse.success();
    }

    @GetMapping("/test")
    public void test(){

        //User user = sysUserServiceImpl.getByPrimaryKey(1l);
        log.info(JSONArray.toJSONString(sysResourceServiceImpl.listAllResourcesGroup()));
        log.info(JSONArray.toJSONString(sysResourceServiceImpl.listResourcesByRoleId(2)));
        //List<User> list = sysUserServiceImpl.listAll();
        //log.info(JSONObject.toJSONString(list));

//        HashMap<String,Object> map = new HashMap<>();
//        map.put("userId",1);
//        map.put("type","menu");
//        ArrayList<Resources> list = (ArrayList<Resources>) sysResourceServiceImpl.listUserResources(map);
//        log.info(JSONArray.toJSONString(list));
//        for(Resources res :list){
//            log.debug(JSONObject.toJSONString(res));
//        }
//        String str = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
        //log.info(str);
    }
}
