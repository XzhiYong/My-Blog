package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@TableName("tb_admin_user")
@Data
public class AdminUser {

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    private Integer adminUserId;

    @TableField("login_user_name")
    private String loginUserName;

    @TableField("login_password")
    private String loginPassword;

    @TableField("nick_name")
    private String nickName;

    @TableField("locked")
    private Integer locked;

    @TableField("login_count")
    private Integer loginCount;

    @TableField("last_login_time")
    private Date lastLoginTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("head_portrait")
    private String headPortrait;
    
    @TableField("mobile")
    private String mobile;
    
    @TableField("email")
    private String email;
    
    @TableField("birthday")
    private String birthday;
    
    @TableField("education")
    private String education;
    
    @TableField("sex")
    private Integer sex;
    
    @TableField("remark")
    private String remark;
    
    @TableField(exist = false)
    private String verificationCode;

    @TableField(exist = false)
    private List<SysRole> sysRole;

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName == null ? null : loginUserName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", adminUserId=").append(adminUserId);
        sb.append(", loginUserName=").append(loginUserName);
        sb.append(", loginPassword=").append(loginPassword);
        sb.append(", nickName=").append(nickName);
        sb.append(", locked=").append(locked);
        sb.append("]");
        return sb.toString();
    }
}
