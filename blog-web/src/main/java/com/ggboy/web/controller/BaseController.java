package com.ggboy.web.controller;

import com.ggboy.common.constant.PropertiesConstant;
import com.ggboy.common.domain.IPage;
import com.ggboy.common.domain.PageVO;
import com.ggboy.common.utils.CacheUtil;
import com.ggboy.core.convert.CoreConvert;
import com.ggboy.core.enums.BlogOrderBy;
import com.ggboy.core.service.BlogService;
import com.ggboy.core.service.CategoryService;
import com.ggboy.system.convert.SystemConvert;
import com.ggboy.system.service.SysConstantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BaseController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SysConstantConfigService sysConstantConfigService;

    @GetMapping("/")
    public String index(ModelMap param) {
        var query = new HashMap<String, Object>(2);
        query.put("orderBy", BlogOrderBy.View.desc());
        var tops = blogService.queryTop();
        var blogList = blogService.queryList(query, new IPage(1, 6));
        param.put("tops", CoreConvert.convertToBlogVOs(tops));
        param.put("blogList", CoreConvert.convertToBlogVOs(blogList));
        setRight(param);
        return "index";
    }

    @GetMapping("/info/{blogId}")
    public String info(HttpServletRequest request, @PathVariable("blogId") String blogId, ModelMap param) {
        var blogVO = CoreConvert.convertToBlogVO(blogService.queryBlogDetail(blogId));
        if (blogVO == null)
            return "redirect:/error/404.html";
        // session 获取查看列表 如果没有则阅读量+1
        var readList = (List<Object>) request.getSession().getAttribute("readList");
        readList = readList != null ? readList : new ArrayList<>(1);
        if (readList.size() == 0 || !readList.contains(blogId)) {
            readList.add(blogId);
            blogService.viewPlusOne(blogId);
            blogVO.setViewCount(blogVO.getViewCount() + 1);
            request.getSession().setAttribute("readList", readList);
        }

        // 获取分类信息
        var categoryList = CoreConvert.convertToCategoryVOs(categoryService.queryCategoryList(blogId));
        var linkBlogList = CoreConvert.convertToBlogVOs(blogService.queryLinkBlog(blogId));

        param.put("blog", blogVO);
        param.put("categoryList", categoryList);
        param.put("linkList", linkBlogList);
        setRight(param);
        return "info";
    }

    @GetMapping("/list")
    public String list() {
        return "redirect:/list/1";
    }

    @GetMapping("/list/{page}")
    public String listPage(@PathVariable("page") Integer page, ModelMap param) {
        return listCategoryPage(null, page, param);
    }

    @GetMapping("/list/category/{categoryId}")
    public String listCategory(@PathVariable("categoryId") Integer categoryId) {
        return "redirect:/list/category/" + categoryId + "/1";
    }

    @GetMapping("/list/category/{categoryId}/{page}")
    public String listCategoryPage(@PathVariable("categoryId") String categoryId, @PathVariable("page") Integer page, ModelMap param) {
        var query = new HashMap<String, Object>(2);
//        query.put("category_id",categoryId);
        query.put("orderBy", BlogOrderBy.View.desc());
        var blogList = blogService.queryList(query, new IPage(page, PropertiesConstant.getDefaultBlogListPageSize()));
        param.put("blogList", CoreConvert.convertToBlogVOs(blogList));
        param.put("page", new PageVO(blogList));
        param.put("categoryId", categoryId);
        setRight(param);
        return "list";
    }


    @GetMapping("/time")
    public String time() {
        return "redirect:/time/1";
    }

    @GetMapping("/time/{page}")
    public String time(@PathVariable("page") Integer page, ModelMap param) {
        var timelineList = blogService.queryTimeLine(new IPage(page, PropertiesConstant.getDefaultTimelinePageSize()));
        param.put("timelineList", CoreConvert.convertToBlogVOs(timelineList));
        param.put("page", new PageVO(timelineList));
        return "time";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @PostMapping("/favorite")
    @ResponseBody
    public String favorite(HttpServletRequest request, HttpServletResponse response, String blogId) {
        Cookie cookie = null;
        for (var item : request.getCookies()) {
            if (item.getName().equals("favorite_cookie")) {
                cookie = item;
                break;
            }
        }

        if (cookie == null)
            cookie = new Cookie("favorite_cookie", "F#>1");

        if (!cookie.getValue().contains(blogId)) {
            blogService.favoritePlusOne(blogId);
            cookie.setValue(cookie.getValue() + "#" + blogId);
        }
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "OK";
    }

    private void setRight(ModelMap param) {
        var friendLink = CacheUtil.get("friendLink");
        if (friendLink == null) {
            friendLink = SystemConvert.convertToFriendLinkVOs(sysConstantConfigService.getFriendLink());
            CacheUtil.put("friendLink", friendLink, 60 * 10);
        }

        var recommendList = CacheUtil.get("recommendList");
        if (recommendList == null) {
            recommendList = CoreConvert.convertToBlogVOs(blogService.querySimpleList(BlogOrderBy.Weight.desc(), new IPage(1)));
            CacheUtil.put("recommendList", recommendList, 60 * 10);
        }

        var favoriteList = CacheUtil.get("favoriteList");
        if (favoriteList == null) {
            favoriteList = CoreConvert.convertToBlogVOs(blogService.querySimpleList(BlogOrderBy.Favorite.desc(), new IPage(1)));
            CacheUtil.put("favoriteList", favoriteList, 60 * 10);
        }

        param.put("friendLink", friendLink);
        param.put("recommendList", recommendList);
        param.put("favoriteList", favoriteList);
    }
}