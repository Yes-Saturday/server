package com.saturday.web.controller;

import com.saturday.common.domain.FrontEndResponse;
import com.saturday.common.utils.CacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/cache")
public class CacheController extends BaseController {

    @GetMapping("/clear")
    public FrontEndResponse cacheClear() {
        CacheUtil.clear();
        return FrontEndResponse.success();
    }

    @GetMapping("/list")
    public FrontEndResponse cacheList() {
        return FrontEndResponse.success(CacheUtil.list());
    }
}