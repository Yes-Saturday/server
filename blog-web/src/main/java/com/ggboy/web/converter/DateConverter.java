package com.ggboy.web.converter;

import com.ggboy.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (StringUtil.isEmpty(source))
            return null;

        return new Date(Long.parseLong(source));
    }
}
