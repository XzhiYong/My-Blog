package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.entity.UserComment;
import com.site.blog.my.core.mapper.UserCommentMapper;
import com.site.blog.my.core.service.UserCommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 夏志勇
 * @since 2023-06-02
 */
@Service
public class UserCommentServiceImpl extends ServiceImpl<UserCommentMapper, UserComment> implements UserCommentService {

    @Override
    public PageInfo<UserComment> commentPage(Integer commentPage) {
        PageHelper.startPage(commentPage, 10);
        List<UserComment> blogComments = baseMapper.selectAllParentCommentNullPage();
        return new PageInfo<>(blogComments);
    }
}
