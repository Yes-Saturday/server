package com.saturday.common.annotation;

import com.saturday.common.utils.RegexUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Name {
        String value();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface NotNull {
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface NotEmpty {
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Size {
        int value() default -1;
        int max() default -1;
        int min() default -1;
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Length {
        int value() default -1;
        int max() default -1;
        int min() default -1;
    }

    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Pattern {
        String value();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Pattern(RegexUtils.MOBILE)
    @interface Mobile {
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Pattern(RegexUtils.EMAIL)
    @interface Email {
    }
}