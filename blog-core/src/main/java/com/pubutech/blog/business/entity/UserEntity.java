package com.pubutech.blog.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserEntity", description = "用户信息请求参数封装类")
public class UserEntity {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "QQ")
    private String qq;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "超级管理员、管理员、普通用户('ROOT','ADMIN','USER')")
    private String userType;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "个人博客地址")
    private String blog;

    @ApiModelProperty(value = "地址")
    private String location;

    @ApiModelProperty(value = "用户来源(默认ZHYD=本网站注册用户)('GITHUB','WEIBO','QQ','ZHYD')")
    private String source;

    @ApiModelProperty(value = "隐私（1：公开，0：不公开）")
    private Integer privacy;

    @ApiModelProperty(value = "通知：(1：通知显示消息详情，2：通知不显示详情)")
    private Integer notification;

    @ApiModelProperty(value = "金币值")
    private Integer score;

    @ApiModelProperty(value = "经验值")
    private Integer experience;

    @ApiModelProperty(value = "注册IP")
    private String regIp;

    @ApiModelProperty(value = "最近登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "最近登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "用户备注")
    private String remark;

    @ApiModelProperty(value = "用户状态")
    private Integer status;

}
