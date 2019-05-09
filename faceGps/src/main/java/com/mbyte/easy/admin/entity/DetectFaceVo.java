package com.mbyte.easy.admin.entity;

import lombok.Data;

/**
 * @ClassName: DetectFaceVo
 * @Description: to do
 * @Author: lxt
 * @Date: 2019-04-30 17:20
 * @Version 1.0
 **/
@Data
public class DetectFaceVo {
    private String age;
    // square: 正方形 triangle:三角形 oval: 椭圆 heart: 心形 round: 圆形
    private String faceShape;
    private String faceShapePro;
    // male:男性 female:女性
    private String gender;
    private String genderPro;
    // human: 真实人脸 cartoon: 卡通人脸
    private String faceType;
    private String faceTypePro;
    // angry:愤怒 disgust:厌恶 fear:恐惧 happy:高兴
    // sad:伤心 surprise:惊讶 neutral:无情绪
    private String emotion;
    private String emotionPro;

    public String getFaceShape() {
        switch (this.faceShape){
            case "square":
                return "正方形";
            case "triangle":
                return "三角形";
            case "oval":
                return "椭圆";
            case "heart":
                return "心形";
            case "round":
                return "圆形";
            default:
                return null;
        }
    }

    public String getGender() {
        switch (this.gender){
            case "male":
                return "男";
            case "female":
                return "女";
            default:
                return null;
        }
    }

    public String getFaceType() {
        switch (this.faceType){
            case "human":
                return "真实人脸";
            case "cartoon":
                return "卡通人脸";
            default:
                return null;
        }
    }

    // angry:愤怒 disgust:厌恶 fear:恐惧 happy:高兴
    // sad:伤心 surprise:惊讶 neutral:无情绪
    public String getEmotion() {
        switch (this.emotion){
            case "angry":
                return "愤怒";
            case "disgust":
                return "厌恶";
            case "fear":
                return "恐惧";
            case "happy":
                return "高兴";
            case "sad":
                return "伤心";
            case "surprise":
                return "惊讶";
            case "neutral":
                return "无情绪";
            default:
                return null;
        }
    }
}
