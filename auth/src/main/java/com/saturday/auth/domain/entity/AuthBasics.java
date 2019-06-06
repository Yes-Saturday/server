package com.saturday.auth.domain.entity;

import com.saturday.auth.enums.AuthBasicsStatus;
import com.saturday.common.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "auth_basics")
public class AuthBasics {
    @Id
    @Column(name = "auth_code")
    private String authCode;
    @Column(name = "auth_name")
    private String authName;
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "status")
    private AuthBasicsStatus status;

    public AuthBasics() {
    }

    public AuthBasics(String authCode, String authName) {
        this.authCode = authCode;
        this.authName = StringUtil.isEmpty(authName) ? authCode : authName;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (this == object)
            return true;
        if (object instanceof AuthBasics)
            return StringUtil.equals(this.authCode, ((AuthBasics) object).authCode);
        return false;
    }

    @Override
    public int hashCode() {
        return this.authCode.hashCode();
    }
}
