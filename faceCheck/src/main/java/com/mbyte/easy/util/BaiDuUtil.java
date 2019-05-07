package com.mbyte.easy.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.imageclassify.AipImageClassify;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人脸识别工具类
 */
public class BaiDuUtil {
    /**
     *  设置APPID/AK/SK
     */
    public static final String APP_ID = "15773933";
    public static final String API_KEY = "Pi9sFpvyQxGGFEPK8CCdGpeP";
    public static final String SECRET_KEY = "SSlVhee7KcKyDR0OLMdnVFhUb7MoavBm";
    public static final String GROUP_ID = "face_check_new";
    public static final int FACE_MIN_SCORE = 70;

    /**
     * 图片格式
     */
    public static final String IMAGE_TYPE_BASE64 = "BASE64";
    /**
     * 获取客户端
     * @return
     */
    public static BaiDuFace getFaceClient(){
        // 初始化一个AipFace
        BaiDuFace client = new BaiDuFace(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }
    /**人脸检测
     *
     * @param imageLocalPath
     * @return
     */
    public static boolean detect(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "1");
        options.put("face_type", "LIVE");
        // 人脸检测
        JSONObject jsonObject = getFaceClient().detect(getImageStr(imageLocalPath), IMAGE_TYPE_BASE64, options);
        return jsonObject.toMap().get("result") != null;
    }
    /**
     * 人脸搜索
     * @return
     */
    public static List<Long> search(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("max_user_num", "20");
        // 人脸搜索
        JSONObject jsonObject = getFaceClient().search(getImageStr(imageLocalPath),IMAGE_TYPE_BASE64, GROUP_ID, options);
        List<Long> userIdList = new ArrayList<>();
        if(jsonObject.toMap().get("result") != null){
            Map map = (Map)jsonObject.toMap().get("result");
            ArrayList<Map> arrayList = ( ArrayList<Map>)map.get("user_list");
            for (Map m : arrayList) {
                //相似度大于该值之后为匹配
                if(Double.valueOf(m.get("score").toString()) > FACE_MIN_SCORE){
                    userIdList.add(Long.valueOf(m.get("user_id").toString()));
                }
            }
        }else{
            return userIdList;
        }
        return userIdList;
    }
    public static List<Long> searchMul(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("max_user_num", "20");
        options.put("max_face_num", "10");
        // 人脸搜索
        JSONObject jsonObject = getFaceClient().searchMul(getImageStr(imageLocalPath),IMAGE_TYPE_BASE64, GROUP_ID, options);
        List<Long> userNameList = new ArrayList<>();
        if(jsonObject.toMap().get("result") != null){
            Map map = (Map)jsonObject.toMap().get("result");
            ArrayList<Map> arrayList = ( ArrayList<Map>)map.get("face_list");
            for (Map m : arrayList) {
                ArrayList<Map> arr =  ( ArrayList<Map>)m.get("user_list");
                //相似度大于该值之后为匹配
                if(Double.valueOf(arr.get(0).get("score").toString()) > FACE_MIN_SCORE){
                    userNameList.add(Long.valueOf(arr.get(0).get("user_id").toString()));
                }
            }
        }else{
            return userNameList;
        }
        return userNameList;
    }
    /**
     * 人脸注册
     * @param imageLocalPath
     * @param userId
     * @return
     */
    public static JSONObject addUser(String imageLocalPath, String userId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        // 人脸注册
        return getFaceClient().addUser(getImageStr(imageLocalPath),IMAGE_TYPE_BASE64, GROUP_ID, userId, options);

    }
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    /**
     * 获取人脸用户id
     * @return
     */
    public  static String getUserId(){
        return new StringBuilder().append(DateUtil.format(null, DateUtil.PATTERN_yyyyMMddHHmmssSSS))
                .append("_").append(Utility.getRandomStrByNum(6)).toString();
    }
    public static void main(String[] args) {
        String imgLocalPath = "/home/lxt/Desktop/0507/刘开胃.jpeg";
        //人脸注册
        System.out.println(addUser(imgLocalPath,getUserId()));
        //人脸搜索
//        System.out.println(search(imgLocalPath));
        //人脸监测
//        System.out.println(detect(imgLocalPath));
    }
}

