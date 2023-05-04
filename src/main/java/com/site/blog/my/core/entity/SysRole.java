package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月23日 13:30
 */
@Data
@TableName("tb_sys_role")
public class SysRole {

    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String name;

    private String code;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String createBy;

    private String updateBy;

    @TableField(exist = false)
    private List<Permission> permissions;
}
