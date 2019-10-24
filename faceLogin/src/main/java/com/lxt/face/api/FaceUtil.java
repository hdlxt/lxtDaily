package com.lxt.face.api;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import sun.net.idn.StringPrep;

import java.util.HashMap;
import java.util.Map;

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
     * @param groupIdList 从指定的group中进行查找 用逗号分隔，上限20个（逗号隔开）
     * @param userId 对指定用户进行搜索，即人脸认证
     * @return
     */
    public static JSONObject search(String image,String imageType,String groupIdList,String userId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id",userId);
//        options.put("max_user_num", "3");
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
     * @return iVBORw0KGgoAAAANSUhEUgAAAUAAAA......
     */
    public static String handleFrontImage(String frontImage){
        if(StringUtils.isEmpty(frontImage)){
            return null;
        }
        return frontImage.split(";")[1].split(",")[1];
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String facemerge() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v1/merge";
        try {
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> image_templateMap = new HashMap<>();
            image_templateMap.put("image", "sfasq35sadvsvqwr5q...");
            image_templateMap.put("image_type", "BASE64");
            image_templateMap.put("quality_control", "NONE");
            map.put("image_template", image_templateMap);
            Map<String, Object> image_targetMap = new HashMap<>();
            image_targetMap.put("image", "sfasq35sadvsvqwr5q...");
            image_targetMap.put("image_type", "BASE64");
            image_targetMap.put("quality_control", "NONE");
            map.put("image_target", image_targetMap);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}

