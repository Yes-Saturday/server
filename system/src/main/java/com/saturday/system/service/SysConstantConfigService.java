package com.saturday.system.service;

import com.saturday.common.constant.ErrorCodeConstant;
import com.saturday.common.constant.PropertiesConstant;
import com.saturday.common.exception.InternalException;
import com.saturday.common.utils.PasswordHandler;
import com.saturday.system.mapper.SysConstantConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysConstantConfigService {

    @Autowired
    private SysConstantConfigMapper sysConstantConfigMapper;

    public List<Map<String, Object>> getFriendLink() {
        List<String> friendLinkList = sysConstantConfigMapper.queryList(PropertiesConstant.getFriendLinkType());

        var result = new ArrayList<Map<String, Object>>(friendLinkList.size());
        for (String item : friendLinkList) {
            String[] tempStr = item.split("<&>");
            if (tempStr.length != 2)
                throw new InternalException(ErrorCodeConstant.SYSTEM_ERROR, "系统内部错误");
            var map = new HashMap<String, Object>(2);
            map.put("name", new String(Base64Utils.decodeFromUrlSafeString(tempStr[0])));
            map.put("url", new String(Base64Utils.decodeFromUrlSafeString(tempStr[1])));
            result.add(map);
        }
        return result;
    }

    public boolean verifyPsd(String psd) {
        return sysConstantConfigMapper.verifyPsd(PasswordHandler.getPwd(psd)) != null;
    }
}