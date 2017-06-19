package live.itrip.app.data.net.request;

import live.itrip.app.config.AppConfig;

/**
 * Created by Feng on 2017/4/28.
 */

public class BaseParams {
    private String apiKey; // api key
    private long timestamp; // 时间戳
    private String op; // op
    private String source;// 来源包括第三方
    private String uid;   // 第三方登录的uid
    private String clientVersion; // 客户端版本
    private String sig;

    public BaseParams() {
        this.setApiKey(AppConfig.API_KEY);
        this.setTimestamp(System.currentTimeMillis());
//        this.setOp("");
        this.setSource(AppConfig.SOURCE);
//        this.setUid("");
        this.setClientVersion(AppConfig.CLIENT_VERSION);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
