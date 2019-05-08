package com.saturday.auth.domain.info;

import com.saturday.common.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthInfo {
    private String name;
    private String code;

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (object instanceof AuthInfo)
            return StringUtil.equals(this.code, ((AuthInfo) object).getCode());
        return false;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}