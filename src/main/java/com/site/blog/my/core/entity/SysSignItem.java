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
 * @since 2023-05-19
 */
@Getter
@Setter
@TableName("tb_sys_sign_item")
public class SysSignItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 签到明细表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("sing_id")
    private Integer singId;

    /**
     * 第几次
     */
    private Integer time;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
