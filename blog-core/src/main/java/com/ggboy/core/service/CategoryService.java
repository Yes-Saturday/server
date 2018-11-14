package com.ggboy.core.service;

import com.ggboy.core.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Map<String, Object>> queryCategoryList(String blogId) {
        return categoryMapper.selectCategoryByBlogId(blogId);
    }
}
