package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 夏志勇
 * @since 2023年05月17日 10:43
 */
@Data
@TableName("tb_blog_fans")
public class BlogFans {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("user_id")
    private AdminUser user;

    @TableField("follower_user_id")
    private Integer followerUserId;

    @TableField("follower_user_id")
    private AdminUser followerUser;

    @TableField("create_time")
    private Date createTime;
}
