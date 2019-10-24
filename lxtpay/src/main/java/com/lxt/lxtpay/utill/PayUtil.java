package com.lxt.lxtpay.utill;

import com.alipay.api.AlipayClient;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: PayUtil
 * @Description: 第三方支付工具类
 * @Author: lxt 
 * @Date: 2019-03-09 09:57
 * @Version 1.0
 **/
public class PayUtil {

    private  volatile static SnowflakeIdWorker snowflakeIdWorker = null;
    /**
     * @Title: handleTotalFee
     * @Description: 处理订单金额
     * @Author: lxt 
     * @param: totalFee 金额单位：元
     * @Date: 2019-02-25 14:08 
     * @return: java.lang.String 返回订单金额单位：分
     * @throws: 
     */
    public static String handleTotalFee(String totalFee){
        if(StringUtils.isBlank(totalFee)){
            return "0";
        }
        return subZeroAndDot(Double.valueOf(totalFee) * 100+"");
    }
   /**
    * @Title: subZeroAndDot
    * @Description: 使用java正则表达式去掉多余的.与0
    * @Author: lxt
    * @param: s
    * @Date: 2019-02-25 14:11
    * @return: java.lang.String
    * @throws:
    */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }
    /**
     * @Title: getInstance
     * @Description: 双重检查　单例模式 创建对象
     * @Author: lxt
     * @Date: 2019-03-09 15:16
     * @return: com.design.background.pay.utill.SnowflakeIdWorker
     * @throws:
     */
    private static SnowflakeIdWorker getInstance(){
        if (snowflakeIdWorker == null) {
            synchronized (AlipayClient.class) {
                if (snowflakeIdWorker == null) {
                    snowflakeIdWorker = new  SnowflakeIdWorker(0, 0);
                }
            }
        }
        return snowflakeIdWorker;
    }
    /**
     * @Title: getSnowflakeIdWorker
     * @Description: 获取SnowflakeIdWorker实例
     * @Author: lxt 
     * @Date: 2019-03-09 15:18 
     * @return: com.design.background.pay.utill.SnowflakeIdWorker
     * @throws: 
     */
    public static SnowflakeIdWorker getSnowflakeIdWorker(){
        return getInstance();
    }
    /**
     * @Title: getUniqueOrderId
     * @Description: 获取唯一订单号
     * @Author: lxt 
     * @Date: 2019-03-09 15:20 
     * @return: java.lang.String
     * @throws: 
     */
    public static String getUniqueOrderId(){
        return String.valueOf(getSnowflakeIdWorker().nextId());
    }
}

