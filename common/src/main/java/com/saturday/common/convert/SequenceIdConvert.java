package com.saturday.common.convert;

import com.saturday.common.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequenceIdConvert {

    private final static Logger log = LoggerFactory.getLogger(SequenceIdConvert.class);

    public final static String convert(String flag, Long number, Integer minLength) {
        if (flag == null)
            flag = "";

        if (minLength == null || minLength < flag.length())
            return flag + number;

        String numStr = String.format("%0" + (minLength - flag.length()) + "d", number);
        return flag + numStr;
    }

    public final static String convertFixedLength(String flag, Long number, Integer length) {
        if (flag == null)
            flag = "";

        if (length == null || length < flag.length())
            throw new InternalException("length_not_good", "["+ flag +"] flag.length > Fixed Length [" + length + "]");

        String numStr = String.format("%0" + (length - flag.length()) + "d", number);

        if (numStr.length() > length)
            throw new InternalException("length_not_good", "[" + numStr + "] length > Fixed Length [" + length + "]");

        numStr = flag + numStr;

        if (numStr.length() == length)
            return numStr;

        log.warn("Flag [" + flag + "] number is already critical. number is " + number);
        return numStr.substring(numStr.length() - length);
    }

    public final static String convert(String flag, Long number) {
        return convert(flag, number, null);
    }

    public final static String convert(Long number) {
        return convert(null, number, null);
    }
}