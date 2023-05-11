package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_blog_comment")
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    private Integer uid;

    private Long blogId;

    @TableField("createTime")
    private Date createTime;

    private String content;

    @TableField("parentCommentId")
    private Integer parentCommentId;


    @TableField("replyCommentId")
    private Integer replyCommentId;

    private boolean remind;

    @TableField(select = false)
    private AdminUser user;

    @TableField(select = false)
    List<BlogComment> replyBlogComments;

    @TableField(select = false)
    private BlogComment replyToBlogComment;


}
