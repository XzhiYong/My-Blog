package com.site.blog.my.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.site.blog.my.core.controller.vo.BlogDetailVO;
import com.site.blog.my.core.controller.vo.BlogListVO;
import com.site.blog.my.core.controller.vo.SimpleBlogListVO;
import com.site.blog.my.core.mapper.BlogCategoryMapper;
import com.site.blog.my.core.mapper.BlogMapper;
import com.site.blog.my.core.mapper.BlogTagMapper;
import com.site.blog.my.core.mapper.BlogTagRelationMapper;
import com.site.blog.my.core.entity.*;
import com.site.blog.my.core.service.BlogCommentService;
import com.site.blog.my.core.service.BlogService;
import com.site.blog.my.core.util.*;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogMapper blogMapper;

    private final BlogCategoryMapper categoryMapper;

    private final BlogTagMapper tagMapper;

    private final BlogTagRelationMapper blogTagRelationMapper;

    private final QiniuUtils qiniuUtils;

    @Lazy
    private final BlogCommentService blogCommentService;

    @Override
    @Transactional
    public String saveBlog(Blog blog) {
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        blog.setAdminUserId(user.getAdminUserId());
        //保存文章
        if (blogMapper.insertSelective(blog) > 0) {
            //新增的tag对象
            List<BlogTag> tagListForInsert = new ArrayList<>();
            //所有的tag对象，用于建立关系数据
            List<BlogTag> allTagsList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                BlogTag tag = tagMapper.selectByTagName(tags[i]);
                if (tag == null) {
                    //不存在就新增
                    BlogTag tempTag = new BlogTag();
                    tempTag.setTagName(tags[i]);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.add(tag);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                tagMapper.batchInsertBlogTag(tagListForInsert);
            }
            if (blogCategory != null) {
                categoryMapper.updateByPrimaryKeySelective(blogCategory);
            }
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            //新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (BlogTag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                //多线程发送短信
//                CompletableFuture.runAsync(() -> {
//
//                });
                return "success";
            }
        }
        return "保存失败";
    }

    @Override
    public PageResult getBlogsPage(PageQueryUtil pageUtil) {
        //判断当前用户是否为系统管理员
        AdminUser adminUser = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        boolean userRole = UserRoleUtil.getUserRole(adminUser);
        if (!userRole) {
            pageUtil.put("userId", adminUser.getAdminUserId());
        }
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        int total = blogMapper.getTotalBlogs(pageUtil);
        return new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return blogMapper.deleteBatch(ids) > 0;
    }

    @Override
    public int getTotalBlogs() {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();

        PageQueryUtil pageUtil = new PageQueryUtil(new HashMap<>());
        pageUtil.put("userId", user.getAdminUserId());
        return blogMapper.getTotalBlogs(pageUtil);
    }

    @Override
    public Blog getBlogById(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (blogForUpdate == null) {
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            BlogTag tag = tagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.batchInsertBlogTag(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        if (blogCategory != null) {
            categoryMapper.updateByPrimaryKeySelective(blogCategory);
        }
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    public PageResult getBlogsForIndexPage(int page) {
        PageQueryUtil params = new PageQueryUtil(new HashMap<>());
        PageResult pageResult = getPageResult(page, params, null);
        return pageResult;
    }

    @NotNull
    private PageResult getPageResult(int page, PageQueryUtil params, Integer userId) {
        params.put("page", page);
        //每页8条
        params.put("limit", 8);
        params.put("blogStatus", 2);//过滤发布状态下的数据
        if (userId != null) {
            params.put("userId", userId);//过滤发布状态下的数据
        }
        List<Blog> blogList = blogMapper.findBlogList(params);
        if (CollUtil.isNotEmpty(blogList)) {
            //最新
            Blog blog = blogList.get(0);
            blog.setTag("最新");
        }
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlogs(params);
        PageResult pageResult = new PageResult(blogListVOS, total, params.getLimit(), params.getPage());
        return pageResult;
    }

    @Override
    public PageResult getBlogsForUserPage(int page, Integer userId) {
        PageQueryUtil params = new PageQueryUtil(new HashMap<>());
        return getPageResult(page, params, userId);
    }

    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        List<Blog> blogs = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogs)) {
            for (Blog blog : blogs) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    @Override
    public BlogDetailVO getBlogDetail(Long id) {
        return getBlogDetailVO(blogMapper.selectByPrimaryKey(id));
    }

    @Override
    public PageResult getBlogsPageByTag(Integer tagId, int page) {
        BlogTag tag = tagMapper.selectByPrimaryKey(tagId);
        if (tag != null && page > 0) {
            Map<String, Object> param = new HashMap();
            param.put("page", page);
            param.put("limit", 9);
            param.put("tagId", tag.getTagId());
            PageQueryUtil pageUtil = new PageQueryUtil(param);
            List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int total = blogMapper.getTotalBlogsByTagId(pageUtil);
            PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            BlogCategory blogCategory = categoryMapper.selectByCategoryName(categoryName);
            if ("默认分类".equals(categoryName) && blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (blogCategory != null && page > 0) {
                Map<String, Object> param = MapUtil.newHashMap(4);
                param.put("page", page);
                param.put("limit", 9);
                param.put("blogCategoryId", blogCategory.getCategoryId());
                param.put("blogStatus", 2);//过滤发布状态下的数据
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.findBlogList(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogs(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map param = new HashMap();
            param.put("page", page);
            param.put("limit", 9);
            param.put("keyword", keyword);
            param.put("blogStatus", 2);//过滤发布状态下的数据
            PageQueryUtil pageUtil = new PageQueryUtil(param);
            List<Blog> blogList = blogMapper.findBlogList(pageUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int total = blogMapper.getTotalBlogs(pageUtil);
            PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        Blog blog = blogMapper.selectBySubUrl(subUrl);
        //不为空且状态为已发布
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        return qiniuUtils.upload(file, "blog");
    }

    @Override
    public boolean updateStatus(Map<String, Object> params) {
        return blogMapper.updateByStatusIds(params);
    }

    @Override
    public List<Long> getUserIdByBlog(Integer adminUserId) {
        return new ArrayList<>();
    }

    /**
     * 方法抽取
     *
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (StringUtils.hasText(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            blogDetailVO.setCommentCount(Math.toIntExact(blogCommentService.count(new LambdaQueryWrapper<BlogComment>()
                .eq(BlogComment::getBlogId, blog.getBlogId()))));
            return blogDetailVO;
        }
        return null;
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }

}
