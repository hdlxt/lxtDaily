package com.mbyte.easy.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.http.AipRequest;
import com.baidu.aip.http.EBodyFormat;
import org.json.JSONObject;

import java.util.HashMap;

public class BaiDuFace extends AipFace {

    public BaiDuFace(String appId, String apiKey, String secretKey) {
        super(appId, apiKey, secretKey);
    }
    public JSONObject searchMul(String image, String imageType, String groupIdList, HashMap<String, String> options) {
        AipRequest request = new AipRequest();
        this.preOperation(request);
        request.addBody("image", image);
        request.addBody("image_type", imageType);
        request.addBody("group_id_list", groupIdList);
        if (options != null) {
            request.addBody(options);
        }

        request.setUri("https://aip.baidubce.com/rest/2.0/face/v3/multi-search");
        request.setBodyFormat(EBodyFormat.RAW_JSON);
        this.postOperation(request);
        return this.requestServer(request);
    }
}
