package com.lxt.face.api;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import sun.net.idn.StringPrep;

import java.util.HashMap;

/**
 * 人脸识别工具类
 */
public class FaceUtil {
    /**
     *  设置APPID/AK/SK
     */
    public static final String APP_ID = "15773933";
    public static final String API_KEY = "Pi9sFpvyQxGGFEPK8CCdGpeP";
    public static final String SECRET_KEY = "SSlVhee7KcKyDR0OLMdnVFhUb7MoavBm";
    /**
     * 图片格式
     */
    public static final String IMAGE_TYPE_BASE64 = "BASE64";
    /**
     * 获取客户端
     * @return
     */
    public static AipFace getClient(){
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

    /**
     * 人脸搜索
     * @param image
     * @param imageType
     * @param groupIdList
     * @return
     */
    public static JSONObject search(String image,String imageType,String groupIdList) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id", "user");
        options.put("max_user_num", "3");
        // 人脸搜索
        return getClient().search(image, imageType, groupIdList, options);
    }

    /**
     * 人脸注册
     * @param image
     * @param imageType
     * @param groupId
     * @param userId
     * @return
     */
    public static JSONObject addUser(String image,String imageType,String groupId,String userId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        // 人脸注册
        return getClient().addUser(image, imageType, groupId, userId, options);

    }
    /**人脸检测
     *
     * @param image
     * @param imageType
     * @return
     */
    public static JSONObject detect(String image,String imageType) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "1");
        options.put("face_type", "LIVE");
        // 人脸检测
        return getClient().detect(image, imageType, options);
    }
    /**
     *处理前台参数获取image参数
     * @param frontImage
     *  eg：image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAA......
     * @return
     */
    public static String handleFrontImage(String frontImage){
        if(StringUtils.isEmpty(frontImage)){
            return null;
        }
        return frontImage.split(";")[1].split(",")[1];
    }


}

