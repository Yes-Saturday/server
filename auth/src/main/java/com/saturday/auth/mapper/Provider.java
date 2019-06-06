package com.saturday.auth.mapper;

import com.saturday.auth.domain.entity.AuthBasics;
import com.saturday.auth.enums.AuthBasicsStatus;
import com.saturday.common.utils.StringUtil;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public class Provider {
    public String insertAuthBasics(@Param("list") List<AuthBasics> authBasicsList) {
        var sb = new StringBuilder();
        sb.append("INSERT INTO").append(" auth_basics ").append("(auth_code,auth_name,group_id,status)").append(" VALUES ");
        for (var i = 0; i < authBasicsList.size(); ++i) {
            sb.append("(#{list[").append(i).append("].authCode}");
            sb.append(",#{list[").append(i).append("].authName}");
            sb.append(",#{list[").append(i).append("].groupId}");
            sb.append(",#{list[").append(i).append("].status}),");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public String updateStatusByAuthCode(@Param("list") List<String> authCodeList, @Param("status") AuthBasicsStatus status) {
        var sb = new StringBuilder();
        sb.append("UPDATE").append(" auth_basics ").append("SET status = #{status} WHERE ");
        sb.append("auth_code IN ").append(buildIN("list", authCodeList.size()));
        return sb.toString();
    }

    public String selectAuthGroupMaxRgt(String pid) {
        var sb = new StringBuilder();
        sb.append("select IFNULL(max(auth_group.rgt), 0) from").append(" auth_group ");
        if (!StringUtil.isEmpty(pid))
            sb.append("where pid = #{arg0}");
        return sb.toString();
    }

    private final static String buildIN(String name, int size) {
        var sb = new StringBuilder().append("(");
        for (var i = 0; i < size; ++i) {
            if (i > 0)
                sb.append(",");
            sb.append("#{").append(name).append("[").append(i).append("]}");
        }
        return sb.append(")").toString();
    }
}