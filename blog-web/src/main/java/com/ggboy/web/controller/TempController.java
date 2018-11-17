package com.ggboy.web.controller;

import com.ggboy.common.annotation.Verify;
import com.ggboy.common.utils.CacheUtil;
import com.ggboy.core.convert.CoreConvert;
import com.ggboy.core.domain.request.UpdateBlogRequest;
import com.ggboy.core.service.BlogService;
import com.ggboy.core.service.CategoryService;
import com.ggboy.system.service.SysConstantConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TempController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SysConstantConfigService sysConstantConfigService;
    private final static String baseUrl = "admin/";

    @GetMapping()
    public String index() {
        return baseUrl + "index";
    }

    @GetMapping("/blog")
    public String blog(String blogId, ModelMap map) {
        var blogDetail = CoreConvert.convertToBlogVO(blogService.queryForUpdate(blogId));
        if (blogDetail == null) {
            return BaseController._404_PAGE;
        }
        map.put("blog", blogDetail);
        return baseUrl + "blog";
    }

    @PostMapping("/update/blog")
    public String updateBlog(@Verify UpdateBlogRequest updateBlogRequest) {
        var param = CoreConvert.convertToUpdateDO(updateBlogRequest);
        blogService.update(param);
        return "redirect:/info/" + updateBlogRequest.getId();
    }

    private void setBaseCategory(ModelMap param) {
        var baseCategoryList = CacheUtil.get("baseCategoryList");
        if (baseCategoryList == null) {
            baseCategoryList = CoreConvert.convertToCategoryVOs(categoryService.queryBase());
            CacheUtil.put("baseCategoryList", baseCategoryList, 60 * 10);
        }

        param.put("baseCategoryList", baseCategoryList);
    }
}