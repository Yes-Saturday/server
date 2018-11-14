package com.ggboy.common.constant;

import java.nio.charset.Charset;

public class BaseConstant {
	public static final long VERSION = 1L;
	public static final String DEFAULT_CHARSET = "utf-8";
	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	public static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	private BaseConstant() {
	}
}
