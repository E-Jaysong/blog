package com.pubutech.blog.core.interceptor;

import com.pubutech.blog.business.consts.SessionConst;
import com.pubutech.blog.business.entity.User;
import com.pubutech.blog.business.service.SysUserService;
import com.pubutech.blog.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURL().toString());
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return true;
        }
        Session session = subject.getSession(true);
        if (session.getAttribute(SessionConst.USER_SESSION_KEY) != null) {
            return true;
        }
        if(!subject.isRemembered()) {
            log.warn("未设置“记住我”,跳转到登录页...");
            //response.sendRedirect(request.getContextPath() + "/passport/login");
            response.sendRedirect("https://www.baidu.com/");
            return false;
        }
        try {
            Long userId = Long.parseLong(subject.getPrincipal().toString());
            User user = userService.getByPrimaryKey(userId);
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), PasswordUtil.decrypt(user.getPassword(), user.getUsername()), true);
            subject.login(token);
            session.setAttribute(SessionConst.USER_SESSION_KEY, user);
            log.info("[{}] - 已自动登录", user.getUsername());
        } catch (Exception e) {
            log.error("自动登录失败", e);
            //response.sendRedirect(request.getContextPath() + "/passport/login");
            response.sendRedirect("https://www.baidu.com/");
            return false;
        }
        return true;
    }

}
