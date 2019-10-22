package com.lxt.lxtpay.module.unionpay.controller;

import com.lxt.lxtpay.model.ProductModel;
import com.lxt.lxtpay.module.unionpay.sdk.AcpService;
import com.lxt.lxtpay.module.unionpay.sdk.LogUtil;
import com.lxt.lxtpay.module.unionpay.sdk.SDKConstants;
import com.lxt.lxtpay.module.unionpay.service.UnionPayService;
import com.lxt.lxtpay.module.unionpay.util.UnionPayConstants;
import com.lxt.lxtpay.utill.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: UnionPayController
 * @Description: to do
 * @Author: lxt
 * @Date: 2019-02-25 11:29
 * @Version 1.0
 **/
@Controller
@RequestMapping("unionPay")
public class UnionPayController {
    private final static Logger logger = LoggerFactory.getLogger(UnionPayController.class);
    private final static String BASE_URL="pay/unionpay/";
    
    @Autowired
    private UnionPayService unionPayService;
    /**
     * @Title: test
     * @Description: 
     * @Author: lxt 
     * @Date: 2019-02-25 14:39
 * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping(value="test")
    public String test(Model model) {
        model.addAttribute("orderId", DateUtil.format(null,DateUtil.PATTERN_yyyyMMddHHmmssSSS));
        model.addAttribute("txnTime", DateUtil.format(null,DateUtil.PATTERN_yyyyMMddHHmmss));
        return "test/test-unionpay";
    }
    /**
     * @Title: pcPay
     * @Description: 电脑网站支付
     * @Author: lxt 
     * @param: model
     * @param: product
     * @Date: 2019-02-25 13:54 
     * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping(value="payPC",method= RequestMethod.POST)
    public String payPC(Model model, ProductModel product) {
        model.addAttribute("form",  unionPayService.payPC(product).getMessage());
        return BASE_URL+"pay-pc";
    }
    /**
     * @Title: backRcvResponse
     * @Description: 银联后台回调接口
     * @Author: lxt 
     * @param: req
     * @param: resp
     * @Date: 2019-02-25 14:36 
     * @throws: 
     */
    @RequestMapping(value="backRcvResponse",method= RequestMethod.POST)
    public void backRcvResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LogUtil.writeLog("BackRcvResponse接收后台通知开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);
        LogUtil.printRequestLog(reqParam);

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(reqParam, encoding)) {
            LogUtil.writeLog("验证签名结果[失败].");
            //验签失败，需解决验签问题

        } else {
            LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
            //获取后台通知的数据，其他字段也可用类似方式获取
            // 商户订单号
            String orderId =reqParam.get(UnionPayConstants.ORDER_ID);
            // 应答码
            String respCode = reqParam.get(UnionPayConstants.RESP_CODE);
            // 原交易流水号
            String queryId = reqParam.get(UnionPayConstants.QUERY_ID);
            //!!!判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。.
            String txnType = reqParam.get(UnionPayConstants.TXN_TYPE);
            switch (txnType){
                case UnionPayConstants.TXN_TYPE_01:
                    logger.info("下单业务后台验签成功....");
                    break;
                case UnionPayConstants.TXN_TYPE_04:
                    logger.info("退货业务后台验签成功....");
                    break;
                case UnionPayConstants.TXN_TYPE_31:
                    logger.info("消费撤销业务后台验签成功....");
                    break;
                default:
            }
        }
        LogUtil.writeLog("BackRcvResponse接收后台通知结束");
        //返回给银联服务器http 200  状态码
        resp.getWriter().print("ok");
    }
    /**
     * @Title: frontRcvResponse
     * @Description: 银联前台回调
     * @Author: lxt 
     * @param: model
     * @param: req
     * @param: resp
     * @Date: 2019-02-25 14:30 
     * @return: java.lang.String
     * @throws: 
     */
    @RequestMapping(value="frontRcvResponse",method= RequestMethod.POST)
    public String frontRcvResponse(Model model, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");

        String encoding = req.getParameter(SDKConstants.param_encoding);
        LogUtil.writeLog("返回报文中encoding=[" + encoding + "]");
        Map<String, String> respParam = getAllRequestParam(req);

        // 打印请求报文
        LogUtil.printRequestLog(respParam);

        Map<String, String> valideData = null;
        StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = respParam.entrySet()
                    .iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                page.append("<tr><td width=\"30%\" align=\"right\">" + key
                        + "(" + key + ")</td><td>" + value + "</td></tr>");
                valideData.put(key, value);
            }
        }
        if (!AcpService.validate(valideData, encoding)) {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            LogUtil.writeLog("验证签名结果[失败].");
        } else {
            page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            LogUtil.writeLog("验证签名结果[成功].");
            //其他字段也可用类似方式获取
            System.out.println(valideData.get(UnionPayConstants.ORDER_ID));
            String respCode = valideData.get(UnionPayConstants.RESP_CODE);
            //判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        }
        model.addAttribute("result", page.toString());
        LogUtil.writeLog("FrontRcvResponse前台接收报文返回结束");
        return BASE_URL+"success";
    }
    /**
     * 获取请求参数中所有的信息
     * 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>(30);
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                     System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
