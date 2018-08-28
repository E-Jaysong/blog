package com.pubutech.blog.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserPwdRequest", description = "用户密码请求参数封装类")
public class UserPwd {

    @NotNull(message = "用户ID不可为空")
    @ApiModelProperty(value = "用户ID")
    private Long id;

    @NotEmpty(message = "原密码不可为空")
    @ApiModelProperty(value = "原密码")
    private String password;

    @NotEmpty(message = "新密码不可为空")
    @ApiModelProperty(value = "新密码")
    @Size(max = 20, min = 6, message = "新密码长度建议保持在6~20个字符以内")
    private String newPassword;

    @NotEmpty(message = "新密码不可为空")
    @ApiModelProperty(value = "新密码重复")
    @Size(max = 20, min = 6, message = "新密码长度建议保持在6~20个字符以内")
    private String newPasswordRepeat;
}
