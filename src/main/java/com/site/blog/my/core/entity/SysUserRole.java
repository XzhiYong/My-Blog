package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月28日 9:32
 */
@Data
@TableName("tb_sys_user_role")
public class SysUserRole {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String roleId;

    private Integer userId;

    private Date createTime;

    @TableField(exist = false)
    private List<Permission> permissions;

}
