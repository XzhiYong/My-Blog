package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author 夏志勇
 * @since 2023年05月06日 9:55
 */
@Data
@TableName("tb_sms_msg")
public class SmsMsg {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String result;

    private String msg;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String mobile;
}
