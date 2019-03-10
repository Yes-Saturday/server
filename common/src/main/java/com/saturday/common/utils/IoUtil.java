package com.saturday.common.utils;

import com.saturday.common.exception.CommonUtilException;

import java.io.*;

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

    public final static int in2Out(InputStream in, OutputStream out) throws IOException {
        return in2Out(in, out, -1);
    }

    public final static int in2Out(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] dataBuffer = new byte[bufferSize > 0 ? bufferSize : 8 * 1024];
        int dataSize, count = 0;
        while ((dataSize = in.read(dataBuffer)) != -1) {
            out.write(dataBuffer, 0, dataSize);
            out.flush();
            count += dataSize;
        }
        return count;
    }

    public static Object byte2Obj(byte[] data) throws IOException, ClassNotFoundException {
        if (data == null)
            return null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return objectInputStream.readObject();
        } finally {
            if (objectInputStream != null)
                try { objectInputStream.close(); } catch (IOException e) {}
        }
    }

    public static byte[] obj2Byte(Object object) throws IOException {
        if (object == null)
            return null;
        ByteArrayOutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            outputStream = new ByteArrayOutputStream(8 * 1024);
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            return outputStream.toByteArray();
        } finally {
            if (objectOutputStream != null)
                try { objectOutputStream.close(); } catch (IOException e) {}
            if (outputStream != null)
                try { outputStream.close(); } catch (IOException e) {}
        }
    }

    public static void byte2File(byte[] buf, String filePath, String fileName) throws IOException {
        OutputStream out = null;
        try {
            out = getFileOutputStream(filePath, fileName);
            out.write(buf);
        } finally {
            if (out != null)
                try { out.close(); } catch (IOException e) { }
        }
    }

    public static int in2File(InputStream in, String filePath, String fileName) throws IOException {
        OutputStream out = null;
        try {
            out = getFileOutputStream(filePath, fileName);
            return in2Out(in, out);
        } finally {
            if (out != null)
                try { out.close(); } catch (IOException e) { }
        }
    }

    public static OutputStream getFileOutputStream(String filePath, String fileName) throws FileNotFoundException {
        File dir = new File(filePath);
        if (!dir.exists())
            dir.mkdirs();
        return new BufferedOutputStream(new FileOutputStream(filePath + File.separator + fileName));
    }

    public static InputStream getFileInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }
}
