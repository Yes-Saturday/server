package com.saturday.common.utils;

import com.saturday.common.exception.VerifyException;
import com.saturday.common.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;

public class Validator {
    public final static void verify(Class<?> clazz, Map<String, String[]> parameterMap) throws VerifyException {
        for (Field field : clazz.getDeclaredFields()) {
            Name nameAnnotation = field.getAnnotation(Name.class);
            String name = nameAnnotation == null ? field.getName() : nameAnnotation.value();
            String[] values = parameterMap.get(field.getName());

            Size size = field.getAnnotation(Size.class);
            if (size != null && values != null) {
                if (size.value() > 0)
                    Assert.isTrue(values.length == size.value(), "{" + name + "}数组长度不正确");
                if (size.max() > 0)
                    Assert.isFalse(values.length > size.max(), "{" + name + "}数组长度超长");
                if (size.min() > 0)
                    Assert.isFalse(values.length < size.min(), "{" + name + "}数组长度过短");
            }

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
                if (length != null) {
                    if (length.value() > 0)
                        Assert.isTrue(value.length() == length.value(), "{" + name + "}字段长度不正确");
                    if (length.max() > 0)
                        Assert.isFalse(value.length() > length.max(), "{" + name + "}字段长度超长");
                    if (length.min() > 0)
                        Assert.isFalse(value.length() < length.min(), "{" + name + "}字段长度过短");
                }
            }
        }
    }
}