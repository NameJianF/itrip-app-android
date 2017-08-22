package live.itrip.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Feng on 2017/4/28.
 */

public final class SigUtil {
    /**
     * 获取数字签名信息
     *
     * @param json
     * @param secretkey
     * @return
     */
    public static String getSig(String json, String secretkey) {
        StringBuffer buffer = new StringBuffer();
        TreeMap<String, Object> jsonMap = JSON.parseObject(json,
                new TypeReference<TreeMap<String, Object>>() {
                });

        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {

            String key = entry.getKey();
            String strvalue = "";

            if ("sig".equalsIgnoreCase(key)) {
                continue;
            }

            if (entry.getValue() instanceof JSONObject
                    || entry.getValue() instanceof JSONArray) {
                getKeyValue(buffer, entry.getValue());
                continue;
            } else {
                strvalue = entry.getValue().toString();
            }

            buffer.append(String.format("%s=%s", key, strvalue));
        }

        buffer.append(secretkey);
        buffer.append("2016");

        AppLog.d("json value:" + buffer.toString());
        return Md5Utils.getStringMD5(buffer.toString());
    }

    private static void getKeyValue(StringBuffer buffer, Object object) {
        TreeMap<String, Object> jsonMap = JSON.parseObject(
                JSON.toJSONString(object),
                new TypeReference<TreeMap<String, Object>>() {
                });
        for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
            String key = entry.getKey();
            String strvalue = "";

            if (entry.getValue() instanceof JSONObject
                    || entry.getValue() instanceof JSONArray) {
                getKeyValue(buffer, entry.getValue());
            } else {
                strvalue = entry.getValue().toString();
            }

            buffer.append(String.format("%s=%s", key, strvalue));
        }
    }
}
