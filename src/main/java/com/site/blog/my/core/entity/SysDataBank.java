package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-06-05
 */
@Getter
@Setter
@TableName("tb_sys_data_bank")
public class SysDataBank implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源库
     */
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 头像
     */
    private String head;

    /**
     * 一级分类
     */
    private String classify;

    /**
     * 二级分类
     */
    @TableField("classify_item")
    private String classifyItem;

    /**
     * 提交人
     */
    @TableField("crate_user_id")
    private Integer crateUserId;

    /**
     * 提交时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 审核时间
     */
    @TableField(value = "check_time")
    private Date checkTime;

    /**
     * 状态 0 待审核 1 审核通过 2 下架
     */
    private Integer state;


}
