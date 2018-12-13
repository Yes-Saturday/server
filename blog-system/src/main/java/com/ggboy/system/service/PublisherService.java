package com.ggboy.system.service;

import com.ggboy.system.convert.SystemConvert;
import com.ggboy.system.domain.info.PublisherInfo;
import com.ggboy.system.enums.SequenceName;
import com.ggboy.system.mapper.PublisherMapper;
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