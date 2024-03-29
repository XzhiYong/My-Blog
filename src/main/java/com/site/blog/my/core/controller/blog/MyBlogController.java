package com.site.blog.my.core.controller.blog;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.site.blog.my.core.controller.BaseController;
import com.site.blog.my.core.controller.vo.BlogDetailVo;
import com.site.blog.my.core.controller.vo.SysDataBankVo;
import com.site.blog.my.core.entity.*;
import com.site.blog.my.core.util.PageResult;
import com.site.blog.my.core.util.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MyBlogController extends BaseController {

    /**
     * 首页
     *
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     *
     * @return
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            request.setAttribute("user", user);
            request.setAttribute("msgCount", blogMsgService.lambdaQuery().eq(BlogMsg::getUId, user.getAdminUserId()).eq(BlogMsg::getState, 0).count());
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", configService.getAllConfigs());

        getSign(request, user);
        setResource(request, user);
        return "blog/" + theme + "/index";
    }

    /**
     * Categories页面(包括分类数据和标签数据)
     *
     * @return
     */
    @GetMapping({"/categories"})
    public String categories(HttpServletRequest request) {
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("categories", categoryService.getAllCategories());
        request.setAttribute("pageName", "分类页面");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/" + theme + "/category";
    }

    /**
     * 详情页
     *
     * @return
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId) {
        return getDetail(request, blogId, null);
    }

    /**
     * 详情页
     *
     * @return
     */
    @GetMapping("/blog/detail")
    public String detailOne(HttpServletRequest request, @RequestParam("blogId") Long blogId) {
        return getDetail(request, blogId, null);
    }

    @NotNull
    public String getDetail(HttpServletRequest request, Long blogId, Integer commentPage) {
        if (commentPage == null) {
            commentPage = 1;
        }
        BlogDetailVo blogDetailVO = blogService.getBlogDetail(blogId);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
        }
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        PageInfo<BlogComment> commentIPage = blogCommentService.commentPage(commentPage, blogId);
        request.setAttribute("page", commentIPage);
        List<BlogComment> comments = commentIPage.getList();
        request.setAttribute("comments", comments);
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        request.setAttribute("isUser", user != null);
        setResource(request, user);
        return "blog/" + theme + "/detail";
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/tag/{tagId}"})
    public String tag(HttpServletRequest request, @PathVariable("tagId") Integer tagId) {
        return tag(request, tagId, 1);
    }


    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/tag"})
    public String tagName(HttpServletRequest request, @RequestParam("tagName") String tagName) {
        BlogTag blogTag = tagService.selectByTagName(tagName);
        return tag(request, blogTag.getTagId(), 1);
    }

    /**
     * 标签列表页
     *
     * @return
     */
    @GetMapping({"/tag/{tagId}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagId") Integer tagId, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagId, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagId);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        getSign(request, null);
        return "blog/" + theme + "/list";
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category"})
    public String categoryName(HttpServletRequest request, @RequestParam("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    /**
     * 分类列表页
     *
     * @return
     */
    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        getSign(request, null);
        return "blog/" + theme + "/list";
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping({"/search/{keyword}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword) {
        return search(request, keyword, 1);
    }

    /**
     * 搜索列表页
     *
     * @return
     */
    @GetMapping({"/search/{keyword}/{page}"})
    public String search(HttpServletRequest request, @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageBySearch(keyword, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "搜索");
        request.setAttribute("pageUrl", "search");
        request.setAttribute("keyword", keyword);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        getSign(request, null);
        return "blog/" + theme + "/list";
    }


    /**
     * 友情链接页
     *
     * @return
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "友情链接");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinksForLinkPage();
        if (linkMap != null) {
            //判断友链类别并封装数据 0-友链 1-推荐 2-个人网站
            if (linkMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                request.setAttribute("personalLinks", linkMap.get((byte) 2));
            }
        }

        setResource(request, (AdminUser) SecurityUtils.getSubject().getPrincipal());
        request.setAttribute("configurations", configService.getAllConfigs());
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", user);
        return "blog/" + theme + "/link";
    }

    /**
     * 友情链接页
     *
     * @return
     */
    @GetMapping({"/about"})
    public String about(HttpServletRequest request) {
        request.setAttribute("configurations", configService.getAllConfigs());
        request.setAttribute("pageName", "关于");
        return "blog/" + theme + "/about";
    }

    /**
     * 个人主页
     *
     * @return
     */
    @GetMapping({"/home"})
    public String home(HttpServletRequest request, @RequestParam(required = false) Integer userId) {
        AdminUser user;
        if (userId == null) {
            user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        } else {
            user = adminUserService.getUserDetailById(Math.toIntExact(userId));
        }

        request.setAttribute("blogPageResult", blogService.getBlogsForUserPage(1, userId));
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("user", user);
        request.setAttribute("pageName", "详情页");
        request.setAttribute("configurations", configService.getAllConfigs());
        getSign(request, null);
        setResource(request, user);

        return "blog/" + theme + "/home";
    }


    /**
     * 个人设置
     *
     * @return
     */
    @GetMapping({"/set"})
    public String set(HttpServletRequest request) {
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "/admin/login";
        }
        request.setAttribute("user", adminUserService.getUserDetailById(user.getAdminUserId()));
        request.setAttribute("pageName", "账号设置");
        request.setAttribute("configurations", configService.getAllConfigs());
        setResource(request, user);
        return "blog/" + theme + "/set";
    }


    /**
     * 积分商城
     *
     * @return
     */
    @GetMapping({"/shopping"})
    public String shopping(HttpServletRequest request, @RequestParam(required = false) Integer type) {
        type = type == null ? 1 : 2;
        AdminUser user = (AdminUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return "admin/login";
        }
        request.setAttribute("user", adminUserService.getUserDetailById(user.getAdminUserId()));
        request.setAttribute("pageName", "积分商城");
        request.setAttribute("configurations", configService.getAllConfigs());
        Map<String, Object> params = MapUtil.newHashMap(1);
        params.put("userId", user.getAdminUserId());
        List<UserResource> userResources = userResourceService.listVo(params);


        List<Integer> collect = userResources.stream().map(UserResource::getResourceId).collect(Collectors.toList());

        List<SysResource> resources = sysResourceService.list(new LambdaQueryWrapper<SysResource>()
            .notIn(CollUtil.isNotEmpty(collect), SysResource::getId, collect)
            .orderByDesc(SysResource::getCreateTime));

        List<SysResource> resourceList = resources.stream().filter(item -> !collect.contains(item.getId())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(resourceList)) {
            //最新
            SysResource sysResource = resourceList.get(0);
            sysResource.setTag("最新上架");
        }
        request.setAttribute("list", resourceList);

        request.setAttribute("userResources", userResources);
        request.setAttribute("type", type);

        setResource(request, user);
        return "blog/" + theme + "/shopping";
    }

    /**
     * 留言板
     *
     * @return
     */
    @GetMapping({"/message"})
    public String message(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") Integer commentPage) {
        PageInfo<UserComment> commentIPage = userCommentService.commentPage(commentPage);
        request.setAttribute("page", commentIPage);
        List<UserComment> comments = commentIPage.getList();
        request.setAttribute("comments", comments);
        request.setAttribute("configurations", configService.getAllConfigs());
        request.setAttribute("pageName", "留言板");

        AdminUser profile = ShiroUtil.getProfile();
        request.setAttribute("user", profile);
        setResource(request, profile);
        return "blog/" + theme + "/message";
    }

    /**
     * 资料库
     *
     * @return
     */
    @GetMapping({"/dataBank"})
    public String dataBank(HttpServletRequest request) {
        AdminUser profile = ShiroUtil.getProfile();

        request.setAttribute("configurations", configService.getAllConfigs());
        request.setAttribute("pageName", "资料库");
        request.setAttribute("user", profile);
        setResource(request, profile);

        List<SysDataBank> sysDataBanks = sysDataBankService.list();
        Map<String, List<SysDataBank>> collect = sysDataBanks.stream().collect(Collectors.groupingBy(SysDataBank::getClassify));
        SysDataBankVo sysDataBankVo = new SysDataBankVo();
        sysDataBankVo.setStudyList(collect.get("学习资源"));
        sysDataBankVo.setImgList(collect.get("图片资源"));
        sysDataBankVo.setOfficeList(collect.get("办公资源"));
        sysDataBankVo.setToolList(collect.get("工具资源"));
        sysDataBankVo.setSearchList(collect.get("搜索资源"));
        sysDataBankVo.setRecreationList(collect.get("娱乐资源"));
        sysDataBankVo.setDesignList(collect.get("设计资源"));
        request.setAttribute("sysDataBankVo", sysDataBankVo);


        return "blog/" + theme + "/dataBank";
    }

    /**
     * 资料库
     *
     * @return
     */
    @GetMapping({"/music"})
    public String music(HttpServletRequest request) {
   
        request.setAttribute("configurations", configService.getAllConfigs());
        request.setAttribute("pageName", "资料库");
        return "blog/" + theme + "/music";
    }


    /**
     * 关于页面 以及其他配置了subUrl的文章页
     *
     * @return
     */
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetailVo blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/" + theme + "/detail";
        } else {
            return "error/error_400";
        }
    }


}
