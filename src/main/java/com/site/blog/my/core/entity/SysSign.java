package com.site.blog.my.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-05-19
 */
@Getter
@Setter
@TableName("tb_sys_sign")
public class SysSign implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Boolean state;

    @TableField("create_time")
    private Date createTime;

    private Integer reset;

    private Integer day;

    @TableField(exist = false)
    private List<SysSignItem> items;


}
