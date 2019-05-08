package com.saturday.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Set;

public class AnnotationFinder {
    private final Set<Annotation> visited = new HashSet<>();

    public final <T extends Annotation> T find(AnnotatedElement element, Class<T> clazz) {
        try {
            return doFind(element, clazz, visited);
        } finally {
            if (visited != null)
                visited.clear();
        }
    }

    public final static <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> clazz) {
        return doFind(element, clazz, new HashSet<>());
    }

    private final static <T extends Annotation> T doFind(AnnotatedElement element, Class<T> clazz, Set<Annotation> visited) {
        if (clazz == null)
            return null;

        var annotation = element.getDeclaredAnnotation(clazz);

        if (annotation != null)
            return annotation;

        var annos = element.getDeclaredAnnotations();

        for (var anno : annos) {
            // 忽略已查找过的注解，避免循环依赖
            if (!isBaseAnnotation(anno) && visited.add(anno)) {
                var result = doFind(anno.annotationType(), clazz, visited);
                if (result != null)
                    return result;
            }
        }

        return null;
    }

    private final static boolean isBaseAnnotation(Annotation annotation) {
        return annotation != null && isBaseAnnotation(annotation.annotationType());
    }

    private final static boolean isBaseAnnotation(Class<? extends Annotation> annotationType) {
        return annotationType != null && isBaseAnnotation(annotationType.getName());
    }

    private final static boolean isBaseAnnotation(String annotationName) {
        return annotationName != null && annotationName.startsWith("java.lang.annotation");
    }
}