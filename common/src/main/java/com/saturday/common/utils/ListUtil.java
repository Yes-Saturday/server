package com.saturday.common.utils;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtil {
    @SuppressWarnings("unchecked")
    public final static <T> List<T> removeRepeat(List<T> list, Predicate<T> removeTerm) {
        if (list == null)
            return Collections.EMPTY_LIST;
        list = list.stream().distinct().dropWhile(removeTerm).collect(Collectors.toList());
        return list;
    }

    @SuppressWarnings("unchecked")
    public final static <T> List<T> removeRepeat(List<T> list) {
        return removeRepeat(list, data -> false);
    }
}
