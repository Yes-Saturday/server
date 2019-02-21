package com.saturday.system.service;

import com.saturday.system.convert.SystemConvert;
import com.saturday.system.domain.info.PublisherInfo;
import com.saturday.system.mapper.PublisherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    @Autowired
    private PublisherMapper publisherMapper;

    public PublisherInfo query4Login(String loginName, String pwd) {
        var result = publisherMapper.select4Login(loginName, pwd);
        return SystemConvert.convertToPublisherInfo(result);
    }
}