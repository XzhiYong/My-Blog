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
 * @since 2023-05-25
 */
@Getter
@Setter
@TableName("tb_sign_log")
public class SignLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 签到的用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 日志对应的签到记录
     */
    @TableField("sign_id")
    private Long signId;

    /**
     * 签到所得的积分
     */
    private Integer point;

    /**
     * 签到时的IP
     */
    private String ip;

    /**
     * IP所属的地区
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


}
