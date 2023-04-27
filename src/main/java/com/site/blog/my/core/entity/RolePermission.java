package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 夏志勇
 * @since 2023年04月26日 16:29
 */
@Data
@TableName("tb_sys_role_permission")
public class RolePermission {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private Integer roleId;

    private Integer permissionId;

}
