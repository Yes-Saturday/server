package com.ggboy.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EnumConverterFactory implements ConverterFactory<String, Enum> {
    private final Map<Class<?>, EnumConverter> converterMap = new ConcurrentHashMap<>(8);

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        EnumConverter converter = converterMap.get(targetType);
        if (converter != null)
            return converter;
        synchronized (targetType) {
            converter = converterMap.get(targetType);
            if (converter != null)
                return converter;

            converter = new EnumConverter(targetType);
            converterMap.put(targetType, converter);
            return converter;
        }
    }
}

class EnumConverter<T> implements Converter<String, T> {
    private Class<T> type;

    EnumConverter(Class<T> type) {
        this.type = type;
    }

    @Override
    public T convert(String source) {
        for (T t : type.getEnumConstants())
            if (t.toString().equals(source))
                return t;
        return null;
    }
}