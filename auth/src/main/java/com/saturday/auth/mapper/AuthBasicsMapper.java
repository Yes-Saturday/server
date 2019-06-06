package com.saturday.auth.mapper;

import com.saturday.auth.domain.entity.AuthBasics;
import com.saturday.auth.enums.AuthBasicsStatus;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collection;
import java.util.List;

public interface AuthBasicsMapper extends Mapper<AuthBasics> {
    @InsertProvider(type = Provider.class, method = "insertAuthBasics")
    int insertAuthBasics(@Param("list") List<AuthBasics> authBasicsList);

    @UpdateProvider(type = Provider.class, method = "updateStatusByAuthCode")
    int updateStatusByAuthCode(@Param("list") List<String> authCodeList, @Param("status") AuthBasicsStatus status);

    @Select("LOCK TABLES auth_basics WRITE")
    void lockTable();

    @Select("UNLOCK TABLES")
    void unlockTable();
}