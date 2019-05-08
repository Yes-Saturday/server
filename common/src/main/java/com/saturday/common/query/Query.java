package com.saturday.common.query;

import com.saturday.common.domain.IPage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Query {
    private IPage iPage;

    public abstract String[] getColumns();
}