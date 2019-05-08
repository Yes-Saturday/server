package com.saturday.web.controller;

import com.saturday.common.utils.CacheUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {

    @GetMapping("/clear")
    public void cacheClear() {
        CacheUtil.clear();
    }

    @GetMapping("/list")
    public Object cacheList() {
        return CacheUtil.list();
    }
}