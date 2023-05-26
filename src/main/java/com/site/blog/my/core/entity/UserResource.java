package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 夏志勇
 * @since 2023-05-26
 */
@Getter
@Setter
@TableName("tb_user_resource")
public class UserResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户资源绑定表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;


    /**
     * 用户id
     */
    @TableField(exist = false)
    private AdminUser user;

    /**
     * 资源id
     */
    @TableField("resource_id")
    private Integer resourceId;

    /**
     * 资源id
     */
    @TableField(exist = false)
    private SysResource sysResource;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建时间
     */
    @TableField("status")
    private Boolean status;


}
