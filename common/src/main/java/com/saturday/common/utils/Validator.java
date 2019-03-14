package com.saturday.common.utils;

import com.saturday.common.annotation.Verify;
import com.saturday.common.annotation.Verify.*;
import com.saturday.common.exception.AssertException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class Validator {

    public final static void verify(Object object) throws AssertException {
        Assert.isNotNull(object, "校验参数为空");
        for (Field field : object.getClass().getDeclaredFields()) {
            String name = field.getAnnotation(Name.class) == null ? field.getName() : field.getAnnotation(Name.class).value();

            Object value = null;
            try {
                value = object.getClass().getMethod("get" + StringUtil.toUpperCase(field.getName())).invoke(object);
            } catch (Exception e) {
                log.warn("校验时获取参数异常", e);
            }

            if (field.isAnnotationPresent(Verify.class))
                verify(value);

            verifyNotNull(field, value, name);
            verifyEnum(field, value, name);
            verifySize(field, value, name);
            verifyNotEmpty(field, value, name);
            verifyPattern(field, value, name);
            verifyLength(field, value, name);
        }
    }

    private final static void verifyNotNull(Field field, Object value, String name) {
        if (field.isAnnotationPresent(NotNull.class))
            Assert.isNotNull(value, "{" + name + "}字段不能为空");
    }

    private final static void verifyNotEmpty(Field field, Object value, String name) {
        if (field.isAnnotationPresent(NotEmpty.class))
            Assert.isNotEmpty(value, "{" + name + "}字段不能为空");
    }

    private final static void verifyPattern(Field field, Object value, String name) {
        if (value == null)
            return;

        var email = field.getAnnotation(Mobile.class);
        var pattern = field.getAnnotation(Pattern.class);
        System.out.println(field.isAnnotationPresent(Pattern.class));
        if (pattern == null)
            return;

        Assert.isTrue(RegexUtils.isMatch(pattern.value(), value.toString()), "{" + name + "}字段不为正确格式");
    }

    private final static void verifyLength(Field field, Object value, String name) {
        if (value == null)
            return;

        var length = field.getAnnotation(Length.class);
        if (length == null)
            return;

        if (length.value() > 0)
            Assert.isTrue(value.toString().length() == length.value(), "{" + name + "}字段长度不正确");
        if (length.max() > 0)
            Assert.isFalse(value.toString().length() > length.max(), "{" + name + "}字段超长");
        if (length.min() > 0)
            Assert.isFalse(value.toString().length() < length.min(), "{" + name + "}字段过短");
    }

    /**
     * Array/List/Map
     */
    private final static void verifySize(Field field, Object value, String name) {
        if (value == null)
            return;

        var size = field.getAnnotation(Size.class);
        if (size == null)
            return;

        if (value.getClass().isArray())
            return;
        // TODO
    }

    private final static void verifyEnum(Field field, Object value, String name) {
        if (value == null)
            return;

        if (!field.getType().isEnum())
            return;

        boolean flag = false;
        for (Object item : field.getType().getEnumConstants())
            if (item.toString().equals(value)) {
                flag = true;
                break;
            }
        Assert.isTrue(flag, "{" + name + "}参数非法");
    }
}