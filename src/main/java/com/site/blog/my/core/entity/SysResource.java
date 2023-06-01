package com.site.blog.my.core.entity;

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
@TableName("tb_sys_resource")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 个性资源表
     */
    private Integer id;

    /**
     * 类型
     */
    private Integer type;


    /**
     * 名称
     */
    private String name;

    /**
     * 换购所需积分·
     */
    private Integer point;

    /**
     * 资源id
     */
    private String fileId;

    /**
     * 预览图
     */
    private String img;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改时间
     */
    @TableField(exist = false)
    private String tag;


}
