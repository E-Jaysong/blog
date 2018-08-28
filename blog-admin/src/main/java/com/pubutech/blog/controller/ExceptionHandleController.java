package com.pubutech.blog.controller;

import com.pubutech.blog.business.enums.MsgCodeEnum;
import com.pubutech.blog.business.enums.ResponseStatus;
import com.pubutech.blog.framework.base.ObjectResponse;
import com.pubutech.blog.framework.exception.SelfBaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * 统一异常处理类<br>
 * 捕获程序所有异常，针对不同异常，采取不同的处理方式
 *
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandleController {

    /**
     * Shiro权限认证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {UnauthorizedException.class, AccountException.class})
    @ResponseBody
    public ObjectResponse unauthorizedExceptionHandle(Throwable e) {
        e.printStackTrace(); // 打印异常栈
        return new ObjectResponse(MsgCodeEnum.SYS_ERROR.getReturnCode(),
                MsgCodeEnum.SYS_ERROR.getReturnMsg(),e.getLocalizedMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ObjectResponse handle(Throwable e) {
        if (e instanceof SelfBaseException) {
            return new ObjectResponse(MsgCodeEnum.SYS_ERROR.getReturnCode(),
                    MsgCodeEnum.SYS_ERROR.getReturnMsg(),e.getMessage());
        }
        if (e instanceof UndeclaredThrowableException) {
            e = ((UndeclaredThrowableException) e).getUndeclaredThrowable();
        }
        ResponseStatus responseStatus = ResponseStatus.getResponseStatus(e.getMessage());
        if (responseStatus != null) {
            log.error(responseStatus.getMessage());
            new ObjectResponse(MsgCodeEnum.SYS_ERROR.getReturnCode(),
                    MsgCodeEnum.SYS_ERROR.getReturnMsg(),e.getMessage());
        }
        e.printStackTrace(); // 打印异常栈
        return new ObjectResponse(MsgCodeEnum.SYS_ERROR.getReturnCode(),
                MsgCodeEnum.SYS_ERROR.getReturnMsg(),e.getMessage());
    }

}
