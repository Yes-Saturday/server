package com.ggboy.common.utils;

import java.util.Arrays;

public class ArrayUtil {
    public static boolean isEmpty(Object[] data) {
        return data == null || data.length == 0;
    }

    public static String toString(Object[] data, String delimiter) {
        if (isEmpty(data))
            return StringUtil.Empty;

        StringBuilder sb = new StringBuilder();
        for (Object item : data) {
            if (sb.length() != 0 && !StringUtil.isEmpty(delimiter))
                sb.append(delimiter);
            if (item != null)
                sb.append(item.toString());
        }

        return sb.toString();
    }

    public final static <T> T[] merge(T[] array1, T[] array2) {
        boolean array1Flag = isEmpty(array1);
        boolean array2Flag = isEmpty(array2);
        if (array1Flag && array2Flag) return null;
        if (array1Flag) return array2;
        if (array2Flag) return array1;
        T[] array = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);
        return array;
    }

    public final static byte[] merge(byte[] array1, byte[] array2) {
        return merge(array1, array2, 0, array2.length);
    }

    public final static byte[] merge(byte[] array1, byte[] array2, int off, int length) {
        if (array1 == null && array2 == null)
            throw new NullPointerException("array can not be null");

        if (array2 == null)
            return array1;

        if (off + length > array2.length)
            throw new ArrayIndexOutOfBoundsException();

        if (array1 == null) {
            return Arrays.copyOfRange(array2, off, off + length);
        }
        byte[] array = Arrays.copyOf(array1, array1.length + length);
        System.arraycopy(array2, off, array, array1.length, length);
        return array;
    }

    public final static byte[] subArray(byte[] data, int start, int end) {
        return Arrays.copyOfRange(data, start, end);
    }
}
