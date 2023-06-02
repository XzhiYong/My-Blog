package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
@Getter
@Setter
@TableName("tb_user_comment")
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    private Integer uid;

    @TableField(value = "createTime",fill = FieldFill.INSERT)
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
