package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author 夏志勇
 * @since 2023年04月24日 16:37
 */
@Data
@TableName("tb_sys_permission")
public class Permission {

    private Integer id;

    @TableField("p_id")
    private Integer pId;
    
    private String name;

    @TableField(exist = false)
    private List<Permission> permissions;
}
