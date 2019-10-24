package com.lxt.geetest.test;

/**
 * GeetestWeb配置文件
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "978b73ea94b4393026524553045ed2ab";
	private static final String geetest_key = "7cd60bfef0a65a78ace8ba085aad023d";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
