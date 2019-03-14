package com.saturday.common.utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Set;

public class AnnotationFinder {
    private final Set<Class<? extends Annotation>> annotations;

    private AnnotationFinder() {
        annotations = new HashSet<>();
    }

    public final static <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> clazz) {
        return new AnnotationFinder().find0(element, clazz);
    }

    public final <T extends Annotation> T find(AnnotatedElement element, Class<T> clazz) {
        try {
            return find0(element, clazz);
        } finally {
            if (annotations != null)
                annotations.clear();
        }
    }

    private final <T extends Annotation> T find0(AnnotatedElement element, Class<T> clazz) {
        if (clazz == null)
            return null;

        var annotation = element.getAnnotation(clazz);

        if (annotation != null)
            return annotation;

        var annos = element.getAnnotations();

        for (var anno : annos) {
            if (isBaseAnnotation(anno))
                continue;

            // 忽略已查找过的注解，避免循环依赖
            if (!annotations.add(anno.getClass()))
                continue;

            return find(anno.getClass(), clazz);
        }

        return null;
    }

    private final static boolean isBaseAnnotation(Annotation annotation) {
        return annotation instanceof Target || annotation instanceof Documented || annotation instanceof Inherited || annotation instanceof Retention;
    }
}