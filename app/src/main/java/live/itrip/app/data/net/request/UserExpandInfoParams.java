package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/8/21.
 */

public class UserExpandInfoParams extends BaseParams {
    private Long uid;   // 用户uid
    private String email; // email
    private String sid; // token


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
