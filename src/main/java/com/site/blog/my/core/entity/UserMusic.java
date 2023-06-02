package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-06-02
 */
@Getter
@Setter
@TableName("tb_user_music")
public class UserMusic implements Serializable {
    

    private static final long serialVersionUID = 1L;

    /**
     * 音乐表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 播放路径
     */
    private String url;

    /**
     * 背景
     */
    private String pic;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
