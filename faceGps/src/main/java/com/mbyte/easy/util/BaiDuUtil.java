package com.mbyte.easy.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import com.baidu.aip.imageclassify.AipImageClassify;
import com.mbyte.easy.admin.entity.DetectFaceVo;
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
    public static final String GROUP_ID = "face_gps_new";
    public static final String USER_ID = "face_user_id";
    public static final int FACE_MIN_SCORE = 80;

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
        options.put("face_field", "age,face_shape,gender,eye_status,face_type");
        options.put("max_face_num", "10");
        options.put("face_type", "LIVE");
        // 人脸检测
        JSONObject jsonObject = getFaceClient().detect(getImageStr(imageLocalPath), IMAGE_TYPE_BASE64, options);
        return jsonObject.toMap().get("result") != null;
    }

    public static List<DetectFaceVo> detectNew(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age,face_shape,gender,face_type,emotion");
        options.put("max_face_num", "10");
        options.put("face_type", "LIVE");
        // 人脸检测
        JSONObject jsonObject = getFaceClient().detect(getImageStr(imageLocalPath), IMAGE_TYPE_BASE64, options);
        if(jsonObject.toMap().get("result") == null){
            return null;
        }
        Map map = (Map)jsonObject.toMap().get("result");
        ArrayList<Map> arrayList = ( ArrayList<Map>)map.get("face_list");
        List<DetectFaceVo> detectFaceVos = new ArrayList<>();
        for (Map m : arrayList) {
            DetectFaceVo detectFaceVo = new DetectFaceVo();
            detectFaceVo.setAge(m.get("age").toString());
            detectFaceVo.setFaceShape(((Map)m.get("face_shape")).get("type").toString());
            detectFaceVo.setFaceShapePro(((Map)m.get("face_shape")).get("probability").toString());
            detectFaceVo.setFaceType(((Map)m.get("face_type")).get("type").toString());
            detectFaceVo.setFaceTypePro(((Map)m.get("face_type")).get("probability").toString());
            detectFaceVo.setEmotion(((Map)m.get("emotion")).get("type").toString());
            detectFaceVo.setEmotionPro(((Map)m.get("emotion")).get("probability").toString());
            detectFaceVo.setGender(((Map)m.get("gender")).get("type").toString());
            detectFaceVo.setGenderPro(((Map)m.get("gender")).get("probability").toString());
            detectFaceVos.add(detectFaceVo);
        }
        return detectFaceVos;
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
        options.put("match_threshold", "60");
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

    /**
     * 人脸删除接口
     * @param faceToken
     */
    public static void delete(String faceToken){
        JSONObject jsonObject =  getFaceClient().faceDelete(USER_ID,GROUP_ID,faceToken,new HashMap<String, String>());
        System.out.println(jsonObject);
    }
    /**
     *  M：1人脸搜索
     * @return
     */
    public static boolean searchMul(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("max_user_num", "20");
        options.put("max_face_num", "10");
        // 人脸搜索
        JSONObject jsonObject = getFaceClient().searchMul(getImageStr(imageLocalPath),IMAGE_TYPE_BASE64, GROUP_ID, options);
        return jsonObject.toMap().get("result") != null;
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
    public static String addUser(String imageLocalPath) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        // 人脸注册
        JSONObject jsonObject = getFaceClient().addUser(getImageStr(imageLocalPath),IMAGE_TYPE_BASE64, GROUP_ID, USER_ID, options);
        if(jsonObject.toMap().get("result") == null){
            return null;
        }
        Map map = (Map)jsonObject.toMap().get("result");
        return map.get("face_token").toString();

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
        String imgLocalPath = "/home/lxt/Desktop/测试图片/合照/2";
        //人脸注册
//        String faceToken = addUser(imgLocalPath);
//        delete(faceToken);
        //M：1人脸搜索
//        System.out.println(searchMul(imgLocalPath));
        //        System.out.println(search(imgLocalPath));
        //人脸监测
//        System.out.println(detectNew(imgLocalPath));

    }
}

