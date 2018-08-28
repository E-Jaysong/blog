package com.pubutech.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.pubutech.blog.business.annotation.BussinessLog;
import com.pubutech.blog.business.entity.UserPwd;
import com.pubutech.blog.business.enums.MsgCodeEnum;
import com.pubutech.blog.business.service.SysUserService;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.util.RandomUtil;
import com.pubutech.blog.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
@Api(value = "RestAccountController", description = "账户操作相关API")
public class RestAccountController {

    @Autowired
    private SysUserService userService;

    @GetMapping("/dyanmicPwd")
    @ApiOperation(value="获取验证码接口",httpMethod="GET",notes="获取验证码API接口")
    public ObjectResponse dyanmicPwd(){
        String dynamicPwd = RandomUtil.random(6);
        SessionUtil.setKaptcha(dynamicPwd);
        JSONObject data = new JSONObject();
        data.put("dynamicPwd",dynamicPwd);
        ObjectResponse objectResponse = ObjectResponse.success(data);
        return objectResponse;
    }

    @PostMapping("/login")
    @ApiOperation(value="登陆接口",httpMethod="POST",notes="登陆API接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "rememberMe", value = "是否记住", required = true, dataType = "Boolean"),
            @ApiImplicitParam(name = "dynamicPwd", value = "动态密码", dataType = "String")
    })
    @BussinessLog("登陆")
    public ObjectResponse login(String username, String password, boolean rememberMe, String dynamicPwd){
        if (!StringUtils.isEmpty(dynamicPwd)) {
            if(!dynamicPwd.equals(SessionUtil.getKaptcha())){
                return ObjectResponse.result(MsgCodeEnum.WRONG_DYNAMICPWD);
            }
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
            return ObjectResponse.success();
        } catch (Exception e) {
            log.error("登录失败，用户名[{}]", username, e);
            token.clear();
            return new ObjectResponse(MsgCodeEnum.FAIL.getReturnCode(),MsgCodeEnum.FAIL.getReturnMsg(),e);
        }
    }

    @PostMapping("/updatePwd")
    @ApiOperation(value="密码修改接口",httpMethod="POST",notes="密码修改API接口")
    @BussinessLog("密码修改")
    public ObjectResponse updatePwd(@Validated UserPwd userPwd, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return ObjectResponse.fail(bindingResult.getFieldError().getDefaultMessage());
        }
        boolean result = userService.updatePwd(userPwd);
        SessionUtil.removeAllSession();
        return ObjectResponse.success(result ? "密码已修改成功，请重新登录" : "密码修改失败");
    }

    @PostMapping("/logout")
    @ApiOperation(value="退出登陆系统接口",httpMethod="POST",notes="退出登陆系统API接口")
    @BussinessLog("退出登陆系统接口")
    public ObjectResponse logout(){
        // http://www.oschina.net/question/99751_91561
        // 此处有坑： 退出登录，其实不用实现任何东西，只需要保留这个接口即可，也不可能通过下方的代码进行退出
        //SecurityUtils.getSubject().logout();
        // 因为退出操作是由Shiro控制的

        return ObjectResponse.success();
    }


}
