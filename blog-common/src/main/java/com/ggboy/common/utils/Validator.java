package com.ggboy.common.utils;

import com.ggboy.common.annotation.*;
import com.ggboy.common.exception.VerifyException;

import java.lang.reflect.Field;
import java.util.Map;

public class Validator {
    public final static void verify(Class<?> clazz, Map<String, String[]> parameterMap) throws VerifyException {
        for (Field field : clazz.getDeclaredFields()) {
            Name nameAnnotation = field.getAnnotation(Name.class);
            String name = nameAnnotation == null ? field.getName() : nameAnnotation.value();
            String[] values = parameterMap.get(field.getName());
            values = values == null ? new String[] {null} : values;

            for (var value : values) {
                if (StringUtil.isEmpty(value))
                    if (field.isAnnotationPresent(NotNull.class))
                        Assert.isTrue(false, "{" + name + "}不能为空");
                    else
                        continue;

                if (field.getType().isEnum()) {
                    boolean flag = false;
                    for (Object item : field.getType().getEnumConstants())
                        if (item.toString().equals(value)) {
                            flag = true;
                            break;
                        }
                    Assert.isTrue(flag, "{" + name + "}参数非法");
                }

                Length length = field.getAnnotation(Length.class);
                if (length != null)
                    Assert.isTrue(value.length() == length.value(), "{" + name + "}字段长度不正确");

                MaxLength maxLength = field.getAnnotation(MaxLength.class);
                if (maxLength != null)
                    Assert.isFalse(value.length() > maxLength.value(), "{" + name + "}字段长度超长");

                MinLength minLength = field.getAnnotation(MinLength.class);
                if (minLength != null)
                    Assert.isFalse(value.length() < minLength.value(), "{" + name + "}字段长度过短");
            }
        }
    }
}