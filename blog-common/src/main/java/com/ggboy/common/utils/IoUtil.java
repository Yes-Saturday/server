package com.ggboy.common.utils;

import com.ggboy.common.exception.CommonUtilException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IoUtil {

    public static byte[] in2Bytes(InputStream inputStream) throws CommonUtilException {
        return in2Bytes(inputStream, 0);
    }

    public final static byte[] in2Bytes(InputStream in, int bufferSize) throws CommonUtilException {
        if (in == null) {
            throw new CommonUtilException("", "");
        }
        try {
            bufferSize = bufferSize > 0 ? bufferSize : 8 * 1024;
            byte[] dataBuffer = new byte[bufferSize];
            byte[] data = null;
            int dataSize;
            while ((dataSize = in.read(dataBuffer)) != -1) {
                data = ArrayUtil.merge(data, dataBuffer, 0, dataSize);
            }
            return data;
        } catch (IOException e) {
            throw new CommonUtilException("", "");
        }
    }

    public final static byte[] in2BytesByLength(InputStream in, int dataLength) throws CommonUtilException {
        byte[] data = new byte[dataLength];
        in2BytesByLength(in, data, dataLength);
        return data;
    }

    public final static int in2BytesByLength(InputStream in, byte[] data, int dataLength) throws CommonUtilException {
        if (in == null || dataLength <= 0) {
            throw new CommonUtilException("", "");
        }

        try {
            int dataSize;
            int count = 0;
            while (count < dataLength) {
                dataSize = in.read(data, count, dataLength - count);
                if (dataSize == -1) {
                    break;
                }
                count += dataSize;
            }
            return count;
        } catch (IOException e) {
            throw new CommonUtilException("", "");
        }
    }

    public final static int in2Out(InputStream in, OutputStream out) throws CommonUtilException {
        return in2Out(in, out, -1);
    }

    public final static int in2Out(InputStream in, OutputStream out, int bufferSize) throws CommonUtilException {
        if (in == null || out == null) {
            throw new CommonUtilException("", "");
        }
        try {
            bufferSize = bufferSize > 0 ? bufferSize : 8 * 1024;
            byte[] dataBuffer = new byte[bufferSize];
            int dataSize = -1;
            int count = 0;
            while ((dataSize = in.read(dataBuffer)) != -1) {
                out.write(dataBuffer, 0, dataSize);
                count += dataSize;
            }
            return count;
        } catch (IOException e) {
            throw new CommonUtilException("", "");
        }
    }

}
