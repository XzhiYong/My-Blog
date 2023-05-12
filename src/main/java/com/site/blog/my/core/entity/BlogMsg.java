package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author 夏志勇
 * @since 2023年05月12日 15:27
 */
@Data
@TableName("tb_blog_msg")
public class BlogMsg {

    @TableId(type = IdType.AUTO)
    private int id;

    private Integer uId;

    private String msg;

    private String title;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer blogId;


}
