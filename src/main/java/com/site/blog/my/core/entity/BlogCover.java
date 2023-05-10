package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 夏志勇
 * @since 2023年05月10日 16:23
 */
@Data
@TableName("tb_blog_cover")
public class BlogCover {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;

    private Date createTime;
}
