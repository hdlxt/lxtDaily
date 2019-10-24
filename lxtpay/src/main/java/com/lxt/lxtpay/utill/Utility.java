package com.lxt.lxtpay.utill;


import java.security.Principal;
import java.util.Random;

/**
 * 
 * @Title: Utility
 * @Description:公用工具类
 * @Author:杨凯旋
 * @Since:2017年7月20日
 * @Version:1.1.0
 */
public class Utility {
	public static String CHAR_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * @Title: getRandomStrByNum
	 * @Description:  获取不同位数的随机字符串
	 * @Author: lxt
	 * @param: factor
	 * @Date: 2019-02-13 15:25
	 * @return: java.lang.String
	 * @throws:
	 */
	public static String getRandomStrByNum(int factor) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < factor; i++) {
			sb.append(CHAR_STR.charAt(random.nextInt(36)));
		}
		return sb.toString();
	}
	/**
	 * @Title: getRandomByNum
	 * @Description: 获取指定位数的随机数字
	 * @Author: lxt 
	 * @param: factor 位数
	 * @Date: 2019-02-18 14:38 
	 * @return: int
	 * @throws: 
	 */
	public static int getRandomByNum(int factor){
		int min=1,max = 1;
		for (int i = 0; i < factor; i++) {
			if(i < factor-1 ){
				min *= 10;
			}
			max *= 10;
		}
		return getRandomIntInRange(min,max-1);
	}
	/**
	 * @Title: getRandomIntInRange
	 * @Description:  获取指定范围的随机数
	 * @Author: lxt
	 * @param: min 最小值
	 * @param: max 最大值
	 * @Date: 2019-02-18 14:38
	 * @return: int
	 * @throws:
	 */
	public static int getRandomIntInRange(int min, int max) {
		return new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}
}
