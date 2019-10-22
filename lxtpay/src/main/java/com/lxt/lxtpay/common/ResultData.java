package com.lxt.lxtpay.common;

public class ResultData<T> {
	/*
	 * 逻辑状态码——"000000"表示成功   "999999"表示失败  "222222"代表其他
	 */
	public final static int KEY_SUCCESS = 1;
	public final static int KEY_FAIL = 2;
	public final static String CODE_SUCCESS = "000000";
	public final static String CODE_SUCCESS_MSG = "请求成功";
	public final static String CODE_FAIL = "999999";
	public final static String CODE_FAIL_MSG = "请求失败";
	public final static String CODE_OTHER = "222222";
	public final static String CODE_OTHER_MSG = "未知异常";
	/*
	 * 逻辑状态码——"000000"表示成功   "999999"表示失败  "222222"代表其他
	 */
	private String code = "999999";

	/*
	 * 如果发生错误，返回错误信息
	 */
	private String message;
	
	/*
	 * 如果逻辑成功，则返回数据
	 */
	private T data;
	/**
	 * 用户第一次访问url
	 */
	private String fristUrl;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public ResultData<T> setData(T data) {
		this.data = data;
		return this;
	}

	public String getFristUrl() {
		return fristUrl;
	}

	public ResultData<T> setFristUrl(String fristUrl) {
		this.fristUrl = fristUrl;
		return this;
	}

	@Override
	public String toString() {
		return "ResultData [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
	public static ResultData setCodeAndMessage(int key,String successMessage,String errorMessage) {
	
		if(key == 1) {
	
			return new ResultData("000000",successMessage);
		}else {

			return new ResultData("999999",errorMessage);
		}
	}

	public ResultData() {
		super();
	}

	public ResultData(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public static ResultData build(int key, String message){
		return setCodeAndMessage(key,message,message);
	}
	public static ResultData build(String code, String message){
		return setCodeAndMessage(CODE_SUCCESS == code ? KEY_SUCCESS : KEY_FAIL,message,message);
	}
	public static ResultData oK(String message){
		return setCodeAndMessage(KEY_SUCCESS,message,message);
	}
	public static ResultData oK(){
		return setCodeAndMessage(KEY_SUCCESS,null,null);
	}
	public static ResultData fail(String message){
		return setCodeAndMessage(KEY_FAIL,message,message);
	}
	public  boolean isOk(){
	    return CODE_SUCCESS == code;
    }
    public  boolean isFail(){
        return CODE_FAIL == code;
    }
}
