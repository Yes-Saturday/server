package com.saturday.blog.service;

import com.saturday.blog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private BlogMapper blogMapper;

    public List<Map<String, Object>> queryList() {
        return blogMapper.selectParentCategory();
    }
}
