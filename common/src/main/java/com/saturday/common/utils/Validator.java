package com.saturday.common.utils;

import com.saturday.common.annotation.Verify;
import com.saturday.common.annotation.Verify.Length;
import com.saturday.common.annotation.Verify.Name;
import com.saturday.common.annotation.Verify.NotEmpty;
import com.saturday.common.annotation.Verify.NotNull;
import com.saturday.common.annotation.Verify.Pattern;
import com.saturday.common.annotation.Verify.Size;
import com.saturday.common.exception.AssertException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Validator {

    private final Map<Object, Set<Class<?>>> objects;
    private final AnnotationFinder annotationFinder;

    public Validator() {
        objects = new HashMap<>();
        annotationFinder = new AnnotationFinder();
    }

    public final static void staticVerify(Object object) throws AssertException {
        Assert.isNotNull(object, "校验参数为空");
        new Validator().verify(object);
    }

    public final void verify(Object object) throws AssertException {
        try {
            Assert.isNotNull(object, "校验参数为空");
            doVerify(object);
        } finally {
            if (objects != null)
                objects.clear();
        }
    }

    private final void doVerify(Object object) throws AssertException {
        doVerify(object, object.getClass());
    }

    private final void doVerify(Object object, Class<?> clazz) throws AssertException {
        if (isVerified(object, clazz))
            return;

        if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class))
            doVerify(object, clazz.getSuperclass());

        for (var field : clazz.getDeclaredFields()) {
            String name = field.getAnnotation(Name.class) == null ? field.getName() : field.getAnnotation(Name.class).value();

            Object value = null;
            try {
                value = object.getClass().getMethod("get" + StringUtil.toUpperCase(field.getName())).invoke(object);
            } catch (Exception e) {
            }

            verifyNotNull(field, value, name);
            verifySize(field, value, name);
            verifyNotEmpty(field, value, name);
            verifyPattern(field, value, name);
            verifyLength(field, value, name);

            if (field.isAnnotationPresent(Verify.class))
                if (field.getType().isArray()) {
                    if (value != null)
                        for (var item : (Object[]) value)
                            doVerify(item, field.getType().getComponentType());
                } else if (value instanceof Collection) {
                    for (var item : (Collection<?>) value)
                        doVerify(item, field.getType().getComponentType());
                } else
                    doVerify(value, field.getType());
        }
    }

    private final void verifyNotNull(Field field, Object value, String name) {
        if (field.isAnnotationPresent(NotNull.class))
            Assert.isNotNull(value, "{" + name + "}字段不能为空");
    }

    private final void verifyNotEmpty(Field field, Object value, String name) {
        if (field.isAnnotationPresent(NotEmpty.class))
            Assert.isNotEmpty(value, "{" + name + "}字段不能为空");
    }

    private final void verifyPattern(Field field, Object value, String name) {
        if (value == null)
            return;

        var pattern = annotationFinder.find(field, Pattern.class);
        if (pattern == null)
            return;

        Assert.isTrue(RegexUtils.isMatch(pattern.value(), value.toString()), "{" + name + "}字段不为正确格式");
    }

    private final void verifyLength(Field field, Object value, String name) {
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

    private final void verifySize(Field field, Object value, String name) {
        if (value == null)
            return;

        var size = field.getAnnotation(Size.class);
        if (size == null)
            return;

        var length = -1;
        if (value.getClass().isArray())
            length = ((Object[]) value).length;
        else if (value instanceof Collection)
            length = ((Collection) value).size();
        else if (value instanceof Map)
            length = ((Map) value).size();

        if (size.value() > 0)
            Assert.isTrue(length == size.value(), "{" + name + "}字段大小不正确");
        if (size.max() > 0)
            Assert.isFalse(length > size.max(), "{" + name + "}字段过大");
        if (size.min() > 0)
            Assert.isFalse(length < size.min(), "{" + name + "}字段过小");
    }

    private final boolean isVerified(Object object, Class<?> clazz) {
        var temp = objects.get(object);

        if (temp == null) {
            objects.put(object, new HashSet<>() {{
                add(clazz);
            }});
            return false;
        }

        return !temp.add(clazz);
    }
}