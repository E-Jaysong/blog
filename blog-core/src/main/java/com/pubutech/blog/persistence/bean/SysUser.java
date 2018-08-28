package com.pubutech.blog.persistence.bean;

import com.pubutech.blog.framework.base.AbstractDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends AbstractDao {

    private String username;

    private String password;

    private String nickname;

    private String mobile;

    private String email;

    private String qq;

    private Date birthday;

    private Integer gender;

    private String avatar;

    @Column(name = "user_type")
    private String userType;

    private String company;

    private String blog;

    private String location;

    private String source;

    private Integer privacy;

    private Integer notification;

    private Integer score;

    private Integer experience;

    @Column(name = "reg_ip")
    private String regIp;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name="last_login_time")
    private Date lastLoginTime;

    @Column(name = "login_count")
    private Integer loginCount;

    private String remark;

    private Integer status;

}
